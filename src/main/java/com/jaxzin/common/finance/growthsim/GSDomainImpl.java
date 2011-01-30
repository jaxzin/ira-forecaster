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

import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;

/**
 * Date: Feb 13, 2006
 * Time: 7:45:01 AM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class GSDomainImpl implements GSDomain {

    private Money initialInvestment;
    //private RegularTimePeriod period;
//    private Date dateWithinFirstPeriod;
//    private Class<? extends RegularTimePeriod> period = Year.class;
    //private int periodCount;
    //private IssueStats issueStats;
    // RoR during employment
    private Dimensionless ror1;
    // RoR during retirement
    private Dimensionless ror2;
    // Adjust dollar values down to represent buying power as of today
    private boolean adjustForInflation;
    // Rate of the risk free benchmark to use to calculate inflation
    private Dimensionless riskFreeRate;
    // initial salary
    private Money initialSalary;
    // paychecks in year
    private int paychecksPerYear;
    // raise percent per period
    private Dimensionless raise;
    // bonus percent
    private Dimensionless bonus;
    // contribution percent
    private Dimensionless contribution;
    // employer matching percent
    private Dimensionless employerMatch;
    // starting age
    private int initialAge;
    // age when ror switches
    //private int rorSwitchAge;
    // retirement age
    private int retirementAge;
    // lifespan
    private int lifespan;
    // Factor of salary required during retirement
    private Dimensionless retirementFactor;

    public Money getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(Money initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public Dimensionless getRor1() {
        return ror1;
    }

    public void setRor1(Dimensionless ror1) {
        this.ror1 = ror1;
    }

    public Dimensionless getRor2() {
        return ror2;
    }

    public void setRor2(Dimensionless ror2) {
        this.ror2 = ror2;
    }

    public boolean isAdjustForInflation() {
        return adjustForInflation;
    }

    public void setAdjustForInflation(boolean adjustForInflation) {
        this.adjustForInflation = adjustForInflation;
    }

    public Dimensionless getRiskFreeRate() {
        return riskFreeRate;
    }

    public void setRiskFreeRate(Dimensionless riskFreeRate) {
        this.riskFreeRate = riskFreeRate;
    }

    public Money getInitialSalary() {
        return initialSalary;
    }

    public void setInitialSalary(Money initialSalary) {
        this.initialSalary = initialSalary;
    }

    public int getPaychecksPerYear() {
        return paychecksPerYear;
    }

    public void setPaychecksPerYear(int paychecksPerYear) {
        this.paychecksPerYear = paychecksPerYear;
    }

    public Dimensionless getRaise() {
        return raise;
    }

    public void setRaise(Dimensionless raise) {
        this.raise = raise;
    }

    public Dimensionless getBonus() {
        return bonus;
    }

    public void setBonus(Dimensionless bonus) {
        this.bonus = bonus;
    }

    public Dimensionless getContribution() {
        return contribution;
    }

    public void setContribution(Dimensionless contribution) {
        this.contribution = contribution;
    }

    public Dimensionless getEmployerMatch() {
        return employerMatch;
    }

    public void setEmployerMatch(Dimensionless employerMatch) {
        this.employerMatch = employerMatch;
    }

    public int getInitialAge() {
        return initialAge;
    }

    public void setInitialAge(int initialAge) {
        this.initialAge = initialAge;
    }

    public int getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(int retirementAge) {
        this.retirementAge = retirementAge;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public Dimensionless getRetirementFactor() {
        return retirementFactor;
    }

    public void setRetirementFactor(Dimensionless retirementFactor) {
        this.retirementFactor = retirementFactor;
    }

    public void validate() {

    }
}
