package com.jaxzin.common.finance.growthsim;

import org.jscience.economics.money.Money;

import java.util.List;

/**
 * Responsible for simulating the growth of an initial investment
 * Date: Feb 10, 2006
 * Time: 10:40:33 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface GrowthSimulator {

    void setDomain(GSDomain domain);
    GSDomain getDomain();

    List<Money> simulate() throws SimulationException;
}
