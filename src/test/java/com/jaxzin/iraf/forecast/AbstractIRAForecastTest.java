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
import com.jaxzin.common.finance.forecast.ForecastDomain;
import com.jaxzin.common.finance.forecast.Forecast;
import com.jaxzin.common.finance.forecast.ForecastException;

/**
 * Date: Feb 13, 2006
 * Time: 9:25:54 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public abstract class AbstractIRAForecastTest extends TestCase {

    protected class CaseData {
        public ForecastDomain domain;
        public Forecast expected;
    }

    private IRAForecaster forecaster;

    public void setUp() {
        forecaster = new IRAForecaster();
    }

    public void testForecast() throws ForecastException {
        CaseData data = getCaseData();
        Forecast actual = forecaster.forecast(data.domain);
        assertTrue("<"+data.expected+"> but was: <"+actual+">", actual.approxEquals(data.expected));
    }

    protected abstract CaseData getCaseData();
}
