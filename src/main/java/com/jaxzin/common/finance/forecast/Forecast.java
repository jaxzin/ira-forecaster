package com.jaxzin.common.finance.forecast;

import com.jaxzin.common.util.ErrorMeasurable;
import org.jscience.economics.money.Money;

import java.util.List;

/**
 * Date: Feb 10, 2006
 * Time: 5:53:55 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface Forecast<T extends Forecast> extends ErrorMeasurable<T> {
    ForecastDomain getDomain();
    List<List<Money>> getForecastData();
}
