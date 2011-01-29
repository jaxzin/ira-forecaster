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
