package com.jaxzin.common.finance.growthsim;

import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;

/**
 * Date: Feb 13, 2006
 * Time: 7:42:52 AM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface GSDomain {
    void setInitialInvestment(Money initialInvestment);
    Money getInitialInvestment();

//    Date getDateWithinFirstPeriod();
//
//    void setDateWithinFirstPeriod(Date dateWithinFirstPeriod);
//
//    Class<? extends RegularTimePeriod> getPeriod();

//    void setPeriod(Class<? extends RegularTimePeriod> period);

    Dimensionless getRor1();

    void setRor1(Dimensionless ror1);

    Dimensionless getRor2();

    void setRor2(Dimensionless ror2);

    Money getInitialSalary();

    void setInitialSalary(Money initialSalary);

    Dimensionless getRaise();

    void setRaise(Dimensionless raise);

    Dimensionless getBonus();

    void setBonus(Dimensionless bonus);

    Dimensionless getContribution();

    void setContribution(Dimensionless contribution);

    Dimensionless getEmployerMatch();

    void setEmployerMatch(Dimensionless employerMatch);

    int getInitialAge();

    void setInitialAge(int initialAge);

//    int getRorSwitchAge();
//
//    void setRorSwitchAge(int rorSwitchAge);

    int getRetirementAge();

    void setRetirementAge(int retirementAge);

    int getLifespan();

    void setLifespan(int lifespan);

    Dimensionless getRetirementFactor();

    void setRetirementFactor(Dimensionless retirementFactor);

    int getPaychecksPerYear();

    void setPaychecksPerYear(int paychecksPerYear);

    boolean isAdjustForInflation();

    void setAdjustForInflation(boolean adjustForInflation);

    Dimensionless getRiskFreeRate();

    void setRiskFreeRate(Dimensionless riskFreeRate);

    void validate() throws InvalidDomainException;
}
