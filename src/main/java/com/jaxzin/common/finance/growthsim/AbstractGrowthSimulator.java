package com.jaxzin.common.finance.growthsim;

import org.jscience.economics.money.Money;

import java.util.List;

/**
 * Date: Feb 10, 2006
 * Time: 11:47:13 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public abstract class AbstractGrowthSimulator implements GrowthSimulator {

    private GSDomain domain;

    public void setDomain(GSDomain domain) {
        this.domain = domain;
    }

    public GSDomain getDomain() {
        return domain;
    }

    final public List<Money> simulate() throws SimulationException {
        try {
            domain.validate();
        } catch (InvalidDomainException e) {
            throw new SimulationException("The specified domain was invalid.", e);
        }

        return simulateAfterValidate();
    }

    abstract protected List<Money> simulateAfterValidate();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AbstractGrowthSimulator that = (AbstractGrowthSimulator) o;

        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;

        return true;
    }

    public int hashCode() {
        return (domain != null ? domain.hashCode() : 0);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AbstractGrowthSimulator");
        sb.append("{domain=").append(domain);
        sb.append('}');
        return sb.toString();
    }
}
