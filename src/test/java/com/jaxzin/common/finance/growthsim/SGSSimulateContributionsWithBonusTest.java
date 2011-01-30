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

import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.economics.money.Money;

import java.util.ArrayList;

/**
 * Date: Feb 12, 2006
 * Time: 6:09:03 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class SGSSimulateContributionsWithBonusTest extends AbstractSGSSimulateTest {

    protected CaseData getCaseData() {
        CaseData cd = new CaseData();

        cd.sim = new StandardGrowthSimulator();
        cd.sim.setDomain(new GSDomainImpl());

        // Locate the simulation start point
        cd.sim.getDomain().setInitialInvestment(Quantity.<Money>valueOf("0 USD"));

        // Setup the data about me
        cd.sim.getDomain().setInitialAge(30);
        cd.sim.getDomain().setRetirementAge(40);
        cd.sim.getDomain().setLifespan(40);

        // Setup the data about my job
        cd.sim.getDomain().setInitialSalary(Quantity.<Money>valueOf("1 USD"));
        cd.sim.getDomain().setBonus(Quantity.<Dimensionless>valueOf("20 %"));
        cd.sim.getDomain().setRaise(Quantity.<Dimensionless>valueOf("0 %"));
        cd.sim.getDomain().setPaychecksPerYear(1);

        // Setup the data about contributions to IRA
        cd.sim.getDomain().setContribution(Quantity.<Dimensionless>valueOf("5 %"));
        cd.sim.getDomain().setEmployerMatch(Quantity.<Dimensionless>valueOf("0 %"));

        // Setup data about the market
        cd.sim.getDomain().setRor1(Quantity.<Dimensionless>valueOf("0 %"));
        cd.sim.getDomain().setRor2(Quantity.<Dimensionless>valueOf("0 %"));
        cd.sim.getDomain().setRiskFreeRate(Quantity.<Dimensionless>valueOf("3 %"));
        cd.sim.getDomain().setAdjustForInflation(false);

        // Setup data about retirement
        cd.sim.getDomain().setRetirementFactor(Quantity.<Dimensionless>valueOf("0 %"));

        cd.expected = new ArrayList<Money>();
        cd.expected.add(Quantity.<Money>valueOf("0.00 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.06 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.12 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.18 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.24 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.30 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.36 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.42 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.48 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.54 USD"));
        cd.expected.add(Quantity.<Money>valueOf("0.60 USD"));

        return cd;
    }
}
