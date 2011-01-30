/**
 * Copyright (C) 2006-2011 Brian R. Jackson <brian@jaxzin.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaxzin.common.finance.growthsim;

import org.jscience.economics.money.Currency;
import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: Feb 10, 2006
 * Time: 11:50:15 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class StandardGrowthSimulator extends AbstractGrowthSimulator {

    private static final Money ZERO_DOLLARS = Quantity.valueOf(0, Currency.USD);

    public List<Money> simulateAfterValidate() {
        GSDomain domain = getDomain();
//        final RegularTimePeriod firstPeriod =
//                RegularTimePeriod.createInstance(
//                        getPeriod(),
//                        getDateWithinFirstPeriod(),
//                        TimeZone.getDefault()
//                );

        final int employedYears = domain.getRetirementAge() - domain.getInitialAge();
        final int retiredYears = domain.getLifespan() - domain.getRetirementAge();

        final Dimensionless ror1Plus1 = (Dimensionless)Dimensionless.ONE.plus(domain.getRor1());
        final Dimensionless ror2Plus1 = (Dimensionless)Dimensionless.ONE.plus(domain.getRor2());
        final Dimensionless raisePlus1 = (Dimensionless)Dimensionless.ONE.plus(domain.getRaise());
        final Dimensionless riskFreePlus1 = (Dimensionless)Dimensionless.ONE.plus(domain.getRiskFreeRate());

        //final TimeSeries ts = new TimeSeries("", getPeriod());
        final List<Money> moneys = new ArrayList<Money>();

        // Simulate the account during the employed years
        Money salary = domain.getInitialSalary();
        Money value = domain.getInitialInvestment();
        Dimensionless riskFree = Dimensionless.ONE;
//        System.out.println("Starting simulation");
        moneys.add(value);
//        System.out.println("0: "+ value.getAmount() + "±"+ value.getAbsoluteError() + " "+ value.getUnit());
        for(int i = 0; i < employedYears; i++) {

            // Calculate investment increase, (1 + ror) * value
            // todo: should this be after contributions?
            value = (Money) ror1Plus1.times(value);
//            System.out.println((i+1)+"- interest: "+ value.getAmount() + "±"+ value.getAbsoluteError() + " "+ value.getUnit());

            // todo: simulate contributions more accurately instead of lump sum at end of year
            // Add the contribution from base salary and bonus
            Money contribution =
                    (Money)salary.times(domain.getContribution().to(Unit.ONE));
            Money contributionBonus = (Money)
                    salary
                       .times(
                            domain.getBonus().times(domain.getContribution())
                       );
            Money totalContribution = (Money)
                    contribution.plus(contributionBonus);

            value = (Money) value.plus(totalContribution);
//            System.out.println((i+1)+"- plus contrib: "+ value.getAmount() + "±"+ value.getAbsoluteError() + " "+ value.getUnit());


            // Add the contribution from the employer matching
            Money employerMatch = (Money)contribution.times(domain.getEmployerMatch());
            Money totalEmployerMatch = (Money) employerMatch.plus(contributionBonus.times(domain.getEmployerMatch()));
            value = (Money) value.plus(totalEmployerMatch);
//            System.out.println((i+1)+"- plus match: "+ value.getAmount() + "±"+ value.getAbsoluteError() + " "+ value.getUnit());

            // Determine intra-year interest on the contribution
            Money intraYearInterestContr = intraYearInterest((Money)contribution.plus(employerMatch), domain.getPaychecksPerYear(), domain.getRor1());
            value = (Money) value.plus(intraYearInterestContr);
//            System.out.println((i+1)+"- plus intrayear: "+ value.getAmount() + "±"+ value.getAbsoluteError() + " "+ value.getUnit());

            if(value.isLessThan(ZERO_DOLLARS)) {
                value = ZERO_DOLLARS;
            }
//            System.out.println((i+1)+"- floor: "+ value.getAmount() + "±"+ value.getAbsoluteError() + " "+ value.getUnit());


            // Record the IRA value at end of year
//            System.out.println((i+1)+": "+ value.getAmount() + "±"+ value.getAbsoluteError() + " "+ value.getUnit());
            moneys.add(domain.isAdjustForInflation() ? (Money) value.divide(riskFree) : value);

            // Get a raise
            salary = (Money) raisePlus1.times(salary);
//            System.out.println("Salary: "+salary.to(Currency.USD));
            // Compound the risk-free rate for the year so we can adjust for inflation
            riskFree = (Dimensionless) riskFree.times(riskFreePlus1);
        }

        // Now simulate the account during retirement years
        for(int i = 0; i < retiredYears; i++) {

            // Calculate investment increase, (1 + ror) * value
            // todo: should this be after withdrawal?
            value = (Money) ror2Plus1.times(value);

            // todo: simulate withdrawal more accurately instead of lump sum at end of year
            // Withdraw the salary+bonus from the account
            Money withdrawal =
                    (Money) salary
                                .plus(
                                        salary
                                            .times(
                                                    domain.getBonus().to(Unit.ONE)
                                            )
                                )
                                .times(
                                        domain.getRetirementFactor().to(Unit.ONE)
                                );
//            System.out.println("Withdrawing: "+withdrawal);
            value = (Money) value.minus(withdrawal);

            if(value.isLessThan(ZERO_DOLLARS)) {
                value = ZERO_DOLLARS;
            }

            // Record the IRA value at end of year
            moneys.add(domain.isAdjustForInflation() ? (Money) value.divide(riskFree) : value);

            // Get a cost-of-living increase since we want to maintain quality of life during retirement
            salary = (Money) riskFreePlus1.times(salary);
            // Compound the risk-free rate for the year so we can adjust for inflation
            riskFree = (Dimensionless) riskFree.times(riskFreePlus1);

        }

//        return ts;
        return moneys;
    }

    static Money intraYearInterest(Money amount, int paychecksPerYear, Dimensionless rate) {
        Dimensionless multiplier = (Dimensionless) Dimensionless.ONE.plus(rate).root(paychecksPerYear);
        Money amountPerCheck = (Money) amount.divide(paychecksPerYear);
        Money value = ZERO_DOLLARS;
        for(int i = 0; i < paychecksPerYear; i++) {
            value = (Money) value.times(multiplier).plus(amountPerCheck);
//            System.out.println(value);
        }
//        System.out.println();

        return (Money)value.minus(amount);
    }

//    private static double adjustGaussian(RandomElement rand, IssueStats issueStats) {
//        return issueStats.getMeanReturn().doubleValue() + rand.gaussian(issueStats.getStandardDeviation().doubleValue());
//    }
}
