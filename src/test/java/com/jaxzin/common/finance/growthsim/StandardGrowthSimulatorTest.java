package com.jaxzin.common.finance.growthsim;

import junit.framework.TestCase;
import org.jscience.economics.money.Currency;
import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;

/**
 * Date: Feb 10, 2006
 * Time: 11:53:15 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class StandardGrowthSimulatorTest extends TestCase {

    public void testIntraYearInterest() throws Exception {
        Money amount = Quantity.valueOf(1000, Currency.USD);
        Money actual = StandardGrowthSimulator.intraYearInterest(amount, 24, Quantity.valueOf(10, NonSI.PERCENT));
        Money expected = Quantity.valueOf(47.12391430086211, Currency.USD);
        assertEquals(expected.doubleValue(), actual.doubleValue());
    }

    public void testJScienceCurrencyPercentBug() {
        // Bug: The following code does not compile due to compiler error: "Quantity cannot be dereferenced"
        //      Update: This is a bug in JDK 5.0, fixed in 6.0: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5003431
        //Money fiftyCents = (Money) Quantity.valueOf("1 USD").times(Quantity.valueOf("50 %"));

        Money fiftyCents = (Money) Quantity.<Money>valueOf("1 USD").times(Quantity.<Dimensionless>valueOf("50 %"));
        try {
            fiftyCents.getCurrency();
            fail("It was excepted that a ClassCastException would be thrown.");
        } catch (ClassCastException ignored) {}
    }
}
