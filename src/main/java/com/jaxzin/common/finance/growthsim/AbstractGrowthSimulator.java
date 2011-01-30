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
