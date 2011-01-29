package com.jaxzin.iraf.forecast;

import junit.framework.TestCase;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Quantity;

import java.util.List;

/**
 * Date: Feb 13, 2006
 * Time: 10:25:24 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class IRAForecastDomainTest extends TestCase {

    public void testCreateCdfInvValues() {
        List<Dimensionless> actual = IRAForecastDomain.createCdfValues(1);
        Dimensionless expected = Quantity.<Dimensionless>valueOf("0");
        assertEquals(1, actual.size());
        assertTrue("expected: <"+expected+"> but was: <"+actual+">", expected.approxEquals(actual.get(0)));
    }
}
