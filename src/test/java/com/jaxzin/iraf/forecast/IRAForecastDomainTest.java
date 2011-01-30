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
