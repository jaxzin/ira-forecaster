package com.jaxzin.common.finance.forecast;

/**
 * Date: Feb 10, 2006
 * Time: 5:52:56 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface Forecaster {

    Forecast forecast(ForecastDomain domain) throws ForecastException;
}

