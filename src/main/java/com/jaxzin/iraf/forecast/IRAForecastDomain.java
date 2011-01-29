package com.jaxzin.iraf.forecast;

import com.jaxzin.common.beans.BoundPropertyHelper;
import com.jaxzin.common.beans.BoundPropertyBean;
import com.jaxzin.common.finance.forecast.ForecastDomain;
import com.jaxzin.common.finance.growthsim.GSDomain;
import com.jaxzin.common.finance.growthsim.GSDomainImpl;
import com.jaxzin.common.finance.growthsim.InvalidDomainException;
import com.jaxzin.common.finance.growthsim.IssueStats;
import com.jaxzin.common.math.MoreMath;
import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.Unit;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: Feb 10, 2006
 * Time: 6:05:19 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class IRAForecastDomain implements ForecastDomain<IRAForecastDomain>, BoundPropertyBean {

    private Money initialInvestment;
    // RoR during employment
    private IssueStats employmentPhaseStats;
    // RoR during retirement
    private IssueStats retirementPhaseStats;
    // Adjust dollar values down to represent buying power as of today
    private boolean adjustForInflation;
    // Rate of the risk free benchmark to use to calculate inflation
    private IssueStats riskFreeStats;
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
    // retirement age
    private int retirementAge;
    // lifespan
    private int lifespan;
    // Factor of salary required during retirement
    private Dimensionless retirementFactor;

    private int multiverseSize;


    private BoundPropertyHelper bph = new BoundPropertyHelper();

    public Money getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(Money initialInvestment) {
        final Money old = this.initialInvestment;
        this.initialInvestment = initialInvestment;
        bph.firePropertyChanged("initialInvestment", old, this.initialInvestment);
    }

    public IssueStats getEmploymentPhaseStats() {
        return employmentPhaseStats;
    }

    public void setEmploymentPhaseStats(IssueStats employmentPhaseStats) {
        final IssueStats old = this.employmentPhaseStats;
        this.employmentPhaseStats = employmentPhaseStats;
        bph.firePropertyChanged("employmentPhaseStats", old, this.employmentPhaseStats);
    }

    public IssueStats getRetirementPhaseStats() {
        return retirementPhaseStats;
    }

    public void setRetirementPhaseStats(IssueStats retirementPhaseStats) {
        final IssueStats old = this.retirementPhaseStats;
        this.retirementPhaseStats = retirementPhaseStats;
        bph.firePropertyChanged("retirementPhaseStats", old, this.retirementPhaseStats);
    }

    public boolean isAdjustForInflation() {
        return adjustForInflation;
    }

    public void setAdjustForInflation(boolean adjustForInflation) {
        final boolean old = this.adjustForInflation;
        this.adjustForInflation = adjustForInflation;
        bph.firePropertyChanged("adjustForInflation", old, this.adjustForInflation);
    }

    public IssueStats getRiskFreeStats() {
        return riskFreeStats;
    }

    public void setRiskFreeStats(IssueStats riskFreeStats) {
        final IssueStats old = this.riskFreeStats;
        this.riskFreeStats = riskFreeStats;
        bph.firePropertyChanged("riskFreeStats", old, this.riskFreeStats);
    }

    public Money getInitialSalary() {
        return initialSalary;
    }

    public void setInitialSalary(Money initialSalary) {
        final Money old = this.initialSalary;
        this.initialSalary = initialSalary;
        bph.firePropertyChanged("initialSalary", old, this.initialSalary);
    }

    public int getPaychecksPerYear() {
        return paychecksPerYear;
    }

    public void setPaychecksPerYear(int paychecksPerYear) {
        final int old = this.paychecksPerYear;
        this.paychecksPerYear = paychecksPerYear;
        bph.firePropertyChanged("paychecksPerYear", old, this.paychecksPerYear);
    }

    public Dimensionless getRaise() {
        return raise;
    }

    public void setRaise(Dimensionless raise) {
        final Dimensionless old = this.raise;
        this.raise = raise;
        bph.firePropertyChanged("raise", old, this.raise);
    }

    public Dimensionless getBonus() {
        return bonus;
    }

    public void setBonus(Dimensionless bonus) {
        final Dimensionless old = this.bonus;
        this.bonus = bonus;
        bph.firePropertyChanged("bonus", old, this.bonus);
    }

    public Dimensionless getContribution() {
        return contribution;
    }

    public void setContribution(Dimensionless contribution) {
        final Dimensionless old = this.contribution;
        this.contribution = contribution;
        bph.firePropertyChanged("contribution", old, this.contribution);
    }

    public Dimensionless getEmployerMatch() {
        return employerMatch;
    }

    public void setEmployerMatch(Dimensionless employerMatch) {
        final Dimensionless old = this.employerMatch;
        this.employerMatch = employerMatch;
        bph.firePropertyChanged("employerMatch", old, this.employerMatch);
    }

    public int getInitialAge() {
        return initialAge;
    }

    public void setInitialAge(int initialAge) {
        final int old = this.initialAge;
        this.initialAge = initialAge;
        bph.firePropertyChanged("initialAge", old, this.initialAge);
    }

    public int getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(int retirementAge) {
        final int old = this.retirementAge;
        this.retirementAge = retirementAge;
        bph.firePropertyChanged("retirementAge", old, this.retirementAge);
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        final int old = this.lifespan;
        this.lifespan = lifespan;
        bph.firePropertyChanged("lifespan", old, this.lifespan);
    }

    public Dimensionless getRetirementFactor() {
        return retirementFactor;
    }

    public void setRetirementFactor(Dimensionless retirementFactor) {
        final Dimensionless old = this.retirementFactor;
        this.retirementFactor = retirementFactor;
        bph.firePropertyChanged("retirementFactor", old, this.retirementFactor);
    }

    public int getMultiverseSize() {
        return multiverseSize;
    }

    public void setMultiverseSize(int multiverseSize) {
        final int old = this.multiverseSize;
        this.multiverseSize = multiverseSize;
        bph.firePropertyChanged("multiverseSize", old, this.multiverseSize);
    }

    public void validate() throws InvalidDomainException {
        // todo: validate domain
    }

    public List<GSDomain> getGSDomainList() {

        List<GSDomain> domains = new ArrayList<GSDomain>(multiverseSize);

        // Create the list of CDF inverse values to use
        List<Dimensionless> cdfValues = createCdfValues(multiverseSize);
        // For each universe...
        for(int i = 0; i < cdfValues.size(); i++) {
            // Fill a stock GSDomain
            GSDomain domain = createStockGSDomain();
            // Get the cdf inv value for 'upward' issues, e.q. we want it to do well in the optimistic universes
            Dimensionless upwardCdfInv = cdfValues.get(i);
            // Get the cdf inv value for 'downward' issues, e.g. we want it to do poorly in the optimistic universes
            Dimensionless downwardCdfInv = cdfValues.get((cdfValues.size()-1) - i);

            // Create the rates
            domain.setRor1(getRate(employmentPhaseStats, upwardCdfInv));
            domain.setRor2(getRate(retirementPhaseStats, upwardCdfInv));
            domain.setRiskFreeRate(getRate(riskFreeStats, downwardCdfInv));

            // Add the domain to the multiverse
            domains.add(domain);
        }
        return domains;
    }

    private Dimensionless getRate(IssueStats stats, Dimensionless cdfInv) {
        return (Dimensionless) cdfInv.times(stats.getStandardDeviation()).plus(stats.getMeanReturn());
    }

    private GSDomain createStockGSDomain() {
        GSDomain domain = new GSDomainImpl();
        domain.setAdjustForInflation(adjustForInflation);
        domain.setBonus(bonus);
        domain.setContribution(contribution);
        domain.setEmployerMatch(employerMatch);
        domain.setInitialAge(initialAge);
        domain.setInitialInvestment(initialInvestment);
        domain.setInitialSalary(initialSalary);
        domain.setLifespan(getLifespan());
        domain.setPaychecksPerYear(getPaychecksPerYear());
        domain.setRaise(getRaise());
        domain.setRetirementAge(getRetirementAge());
        domain.setRetirementFactor(getRetirementFactor());

        return domain;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        bph.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        bph.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        bph.removePropertyChangeListener(listener);
    }

    public List<PropertyChangeListener> getPropertyChangeListeners() {
        return bph.getPropertyChangeListeners();
    }

    static List<Dimensionless> createCdfValues(int count) {
        List<Dimensionless> values = new ArrayList<Dimensionless>(count);
        // Skip 0.0 and stop before we get to 1.0
        for(int i = 1; i < (count+1); i++) {
            values.add(
                    Quantity.valueOf(
                            MoreMath.cdfinv(((double)i)/((double)(count+1))),
                            Unit.ONE
                    )
            );
        }
        return values;
    }

    public boolean approxEquals(IRAForecastDomain that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        if (adjustForInflation != that.adjustForInflation) return false;
        if (initialAge != that.initialAge) return false;
        if (lifespan != that.lifespan) return false;
        if (multiverseSize != that.multiverseSize) return false;
        if (paychecksPerYear != that.paychecksPerYear) return false;
        if (retirementAge != that.retirementAge) return false;
        if (bonus != null ? !bonus.approxEquals(that.bonus) : that.bonus != null) return false;
        if (contribution != null ? !contribution.approxEquals(that.contribution) : that.contribution != null) return false;
        if (employerMatch != null ? !employerMatch.approxEquals(that.employerMatch) : that.employerMatch != null) return false;
        if (employmentPhaseStats != null ? !employmentPhaseStats.approxEquals(that.employmentPhaseStats) : that.employmentPhaseStats != null)
            return false;
        if (initialInvestment != null ? !initialInvestment.approxEquals(that.initialInvestment) : that.initialInvestment != null)
            return false;
        if (initialSalary != null ? !initialSalary.approxEquals(that.initialSalary) : that.initialSalary != null) return false;
        if (raise != null ? !raise.approxEquals(that.raise) : that.raise != null) return false;
        if (retirementFactor != null ? !retirementFactor.approxEquals(that.retirementFactor) : that.retirementFactor != null)
            return false;
        if (retirementPhaseStats != null ? !retirementPhaseStats.approxEquals(that.retirementPhaseStats) : that.retirementPhaseStats != null)
            return false;
        if (riskFreeStats != null ? !riskFreeStats.approxEquals(that.riskFreeStats) : that.riskFreeStats != null) return false;

        return true;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final IRAForecastDomain that = (IRAForecastDomain) o;

        if (adjustForInflation != that.adjustForInflation) return false;
        if (initialAge != that.initialAge) return false;
        if (lifespan != that.lifespan) return false;
        if (multiverseSize != that.multiverseSize) return false;
        if (paychecksPerYear != that.paychecksPerYear) return false;
        if (retirementAge != that.retirementAge) return false;
        if (bonus != null ? !bonus.equals(that.bonus) : that.bonus != null) return false;
        if (contribution != null ? !contribution.equals(that.contribution) : that.contribution != null) return false;
        if (employerMatch != null ? !employerMatch.equals(that.employerMatch) : that.employerMatch != null) return false;
        if (employmentPhaseStats != null ? !employmentPhaseStats.equals(that.employmentPhaseStats) : that.employmentPhaseStats != null)
            return false;
        if (initialInvestment != null ? !initialInvestment.equals(that.initialInvestment) : that.initialInvestment != null)
            return false;
        if (initialSalary != null ? !initialSalary.equals(that.initialSalary) : that.initialSalary != null) return false;
        if (raise != null ? !raise.equals(that.raise) : that.raise != null) return false;
        if (retirementFactor != null ? !retirementFactor.equals(that.retirementFactor) : that.retirementFactor != null)
            return false;
        if (retirementPhaseStats != null ? !retirementPhaseStats.equals(that.retirementPhaseStats) : that.retirementPhaseStats != null)
            return false;
        if (riskFreeStats != null ? !riskFreeStats.equals(that.riskFreeStats) : that.riskFreeStats != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (initialInvestment != null ? initialInvestment.hashCode() : 0);
        result = 29 * result + (employmentPhaseStats != null ? employmentPhaseStats.hashCode() : 0);
        result = 29 * result + (retirementPhaseStats != null ? retirementPhaseStats.hashCode() : 0);
        result = 29 * result + (adjustForInflation ? 1 : 0);
        result = 29 * result + (riskFreeStats != null ? riskFreeStats.hashCode() : 0);
        result = 29 * result + (initialSalary != null ? initialSalary.hashCode() : 0);
        result = 29 * result + paychecksPerYear;
        result = 29 * result + (raise != null ? raise.hashCode() : 0);
        result = 29 * result + (bonus != null ? bonus.hashCode() : 0);
        result = 29 * result + (contribution != null ? contribution.hashCode() : 0);
        result = 29 * result + (employerMatch != null ? employerMatch.hashCode() : 0);
        result = 29 * result + initialAge;
        result = 29 * result + retirementAge;
        result = 29 * result + lifespan;
        result = 29 * result + (retirementFactor != null ? retirementFactor.hashCode() : 0);
        result = 29 * result + multiverseSize;
        return result;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IRAForecastDomain");
        sb.append("{initialInvestment=").append(initialInvestment);
        sb.append(", employmentPhaseStats=").append(employmentPhaseStats);
        sb.append(", retirementPhaseStats=").append(retirementPhaseStats);
        sb.append(", adjustForInflation=").append(adjustForInflation);
        sb.append(", riskFreeStats=").append(riskFreeStats);
        sb.append(", initialSalary=").append(initialSalary);
        sb.append(", paychecksPerYear=").append(paychecksPerYear);
        sb.append(", raise=").append(raise);
        sb.append(", bonus=").append(bonus);
        sb.append(", contribution=").append(contribution);
        sb.append(", employerMatch=").append(employerMatch);
        sb.append(", initialAge=").append(initialAge);
        sb.append(", retirementAge=").append(retirementAge);
        sb.append(", lifespan=").append(lifespan);
        sb.append(", retirementFactor=").append(retirementFactor);
        sb.append(", multiverseSize=").append(multiverseSize);
        sb.append('}');
        return sb.toString();
    }
}
