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
public class SGSSimulateWithdrawalsWithBonusTest extends AbstractSGSSimulateTest {

    protected CaseData getCaseData() {
        CaseData cd = new CaseData();

        cd.sim = new StandardGrowthSimulator();
        cd.sim.setDomain(new GSDomainImpl());

        // Locate the simulation start point
        cd.sim.getDomain().setInitialInvestment(Quantity.<Money>valueOf("100 USD"));

        // Setup the data about me
        cd.sim.getDomain().setInitialAge(30);
        cd.sim.getDomain().setRetirementAge(30);
        cd.sim.getDomain().setLifespan(40);

        // Setup the data about my job
        cd.sim.getDomain().setInitialSalary(Quantity.<Money>valueOf("1 USD"));
        cd.sim.getDomain().setBonus(Quantity.<Dimensionless>valueOf("20 %"));
        cd.sim.getDomain().setRaise(Quantity.<Dimensionless>valueOf("0 %"));
        cd.sim.getDomain().setPaychecksPerYear(1);

        // Setup the data about contributions to IRA
        cd.sim.getDomain().setContribution(Quantity.<Dimensionless>valueOf("0 %"));
        cd.sim.getDomain().setEmployerMatch(Quantity.<Dimensionless>valueOf("0 %"));

        // Setup data about the market
        cd.sim.getDomain().setRor1(Quantity.<Dimensionless>valueOf("0 %"));
        cd.sim.getDomain().setRor2(Quantity.<Dimensionless>valueOf("0 %"));
        cd.sim.getDomain().setRiskFreeRate(Quantity.<Dimensionless>valueOf("3 %"));
        cd.sim.getDomain().setAdjustForInflation(false);

        // Setup data about retirement
        cd.sim.getDomain().setRetirementFactor(Quantity.<Dimensionless>valueOf("100 %"));

        cd.expected = new ArrayList<Money>();
        cd.expected.add(Quantity.<Money>valueOf("100.00 USD"));
        cd.expected.add(Quantity.<Money>valueOf("98.80 USD"));
        cd.expected.add(Quantity.<Money>valueOf("97.60 USD"));
        cd.expected.add(Quantity.<Money>valueOf("96.40 USD"));
        cd.expected.add(Quantity.<Money>valueOf("95.20 USD"));
        cd.expected.add(Quantity.<Money>valueOf("94.00 USD"));
        cd.expected.add(Quantity.<Money>valueOf("92.80 USD"));
        cd.expected.add(Quantity.<Money>valueOf("91.60 USD"));
        cd.expected.add(Quantity.<Money>valueOf("90.40 USD"));
        cd.expected.add(Quantity.<Money>valueOf("89.20 USD"));
        cd.expected.add(Quantity.<Money>valueOf("88.00 USD"));

        return cd;
    }
}
