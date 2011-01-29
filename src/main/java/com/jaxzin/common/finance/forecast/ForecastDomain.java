package com.jaxzin.common.finance.forecast;

import com.jaxzin.common.finance.growthsim.GSDomain;
import com.jaxzin.common.finance.growthsim.InvalidDomainException;
import com.jaxzin.common.finance.growthsim.IssueStats;
import com.jaxzin.common.util.ErrorMeasurable;
import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Date: Feb 10, 2006
 * Time: 5:54:05 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface ForecastDomain<T extends ForecastDomain> extends ErrorMeasurable<T> {

    void setInitialInvestment(Money initialInvestment);
    Money getInitialInvestment();

//    Date getDateWithinFirstPeriod();
//
//    void setDateWithinFirstPeriod(Date dateWithinFirstPeriod);
//
//    Class<? extends RegularTimePeriod> getPeriod();

//    void setPeriod(Class<? extends RegularTimePeriod> period);

    IssueStats getEmploymentPhaseStats();

    void setEmploymentPhaseStats(IssueStats employmentPhaseStats);

    IssueStats getRetirementPhaseStats();

    void setRetirementPhaseStats(IssueStats retirementPhaseStats);

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

    IssueStats getRiskFreeStats();

    void setRiskFreeStats(IssueStats riskFreeRate);

    int getMultiverseSize();

    void setMultiverseSize(int multiverseSize);

    void validate() throws InvalidDomainException;


    /**
     * Responsible for building a list of GSDomain instances that
     * cover the defined forecast multiverse
     * @return A List of GSDomain instances that cover the forecast multiverse defined by this ForecastDomain
     */
    List<GSDomain> getGSDomainList();

    void addPropertyChangeListener(PropertyChangeListener listener);
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    List<PropertyChangeListener> getPropertyChangeListeners();
}
