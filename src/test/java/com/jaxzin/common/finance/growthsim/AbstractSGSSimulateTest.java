package com.jaxzin.common.finance.growthsim;

import junit.framework.TestCase;
import org.jscience.economics.money.Money;

import java.util.List;

/**
 * Date: Feb 10, 2006
 * Time: 11:53:15 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public abstract class AbstractSGSSimulateTest extends TestCase {

    protected static class CaseData {
        public int index;
        public GrowthSimulator sim;
        public List<Money> expected;
    }

//    private static void setupCase0(AbstractSGSSimulateTest.CaseData cd) {
//        cd.sim = new StandardGrowthSimulator();
//
//        // Locate the simulation start point
//        cd.sim.setInitialInvestment(Quantity.valueOf(45000, Currency.USD));
//
//        // Setup the data about me
//        cd.sim.setInitialAge(28);
//        cd.sim.setRetirementAge(65);
//        cd.sim.setLifespan(90);
//
//        // Setup the data about my job
//        cd.sim.setInitialSalary(Quantity.valueOf(95000, Currency.USD));
//        cd.sim.setBonus(Quantity.valueOf(0, NonSI.PERCENT));
//        cd.sim.setRaise(Quantity.valueOf(5.5, NonSI.PERCENT));
//        cd.sim.setPaychecksPerYear(24);
//
//        // Setup the data about contributions to IRA
//        cd.sim.setContribution(Quantity.valueOf(4, NonSI.PERCENT));
//        cd.sim.setEmployerMatch(Quantity.valueOf(75, NonSI.PERCENT));
//
//        // Setup data about the market
//        cd.sim.setRor1(Quantity.valueOf(10, NonSI.PERCENT));
//        cd.sim.setRor2(Quantity.valueOf(5, NonSI.PERCENT));
//        cd.sim.setRiskFreeRate(Quantity.<Dimensionless>valueOf("3.5 %"));
//        cd.sim.setAdjustForInflation(false);
//
//        // Setup data about retirement
//        cd.sim.setRetirementFactor(Quantity.valueOf(70, NonSI.PERCENT));
//
//        cd.expected = new ArrayList<Money>();
//        cd.expected.add(Quantity.<Money>valueOf("1000 USD"));
//    }

    public void testSimulate() throws Exception {
        final CaseData data = getCaseData();
        List<Money> actual = data.sim.simulate();
        AbstractSGSSimulateTest.assertApproxEquals("Case "+data.index, data.expected, actual);
    }


    protected abstract CaseData getCaseData();





    //=============================================================
    // Utility static methods
    //=============================================================

    private static void assertApproxEquals(String message, List<Money> expected, List<Money> actual) {
        boolean approxEquals = expected != null && actual != null && expected.size() == actual.size();

        if(approxEquals) {
            int index = 0;
            for (Money expectedItem : expected) {
                Money actualItem = actual.get(index);
                approxEquals = expectedItem.approxEquals(actualItem);
                if(!approxEquals)
                    break;
                index++;
            }
        }

        if(!approxEquals) {
            message += "; expected: <"+ AbstractSGSSimulateTest.moneyListToString(expected)+"> but was: <"+ AbstractSGSSimulateTest.moneyListToString(actual)+">";
            fail(message);
        }
    }

    private static String moneyListToString(List<Money> moneys) {
        StringBuilder buf = new StringBuilder();
        buf.append('[');
        for (Money money : moneys) {
            buf.append(money.getAmount())
               .append("±")
               .append(money.getAbsoluteError())
               .append(' ')
               .append(money.getUnit())
               .append(", ");
        }
        if(!moneys.isEmpty()) {
            buf.delete(buf.length()-2, buf.length());
        }
        buf.append(']');
        return buf.toString();
    }
}
