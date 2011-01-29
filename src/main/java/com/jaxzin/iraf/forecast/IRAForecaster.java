package com.jaxzin.iraf.forecast;

import com.jaxzin.common.finance.forecast.Forecaster;
import com.jaxzin.common.finance.forecast.Forecast;
import com.jaxzin.common.finance.forecast.ForecastDomain;
import com.jaxzin.common.finance.forecast.ForecastException;
import com.jaxzin.common.finance.growthsim.GrowthSimulator;
import com.jaxzin.common.finance.growthsim.StandardGrowthSimulator;
import com.jaxzin.common.finance.growthsim.GSDomain;
import com.jaxzin.common.finance.growthsim.SimulationException;
import org.jscience.economics.money.Money;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Date: Feb 10, 2006
 * Time: 5:58:23 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class IRAForecaster implements Forecaster {

    public Forecast forecast(ForecastDomain domain) throws ForecastException {
        List<List<Money>> multiverse = new ArrayList<List<Money>>();
        GrowthSimulator sim = new StandardGrowthSimulator();
        try {
            for (Iterator i = domain.getGSDomainList().iterator(); i.hasNext();) {
                GSDomain gsDomain = (GSDomain) i.next();
                sim.setDomain(gsDomain);
                multiverse.add(sim.simulate());
            }
            // todo: why does the following not compile?
//            for (GSDomain gsDomain : domain.getGSDomainList()) {
//                sim.setDomain(gsDomain);
//                multiverse.add(sim.simulate());
//            }
        } catch (SimulationException e) {
            throw new ForecastException("There was a problem with the forecast", e, this);
        }
        return new IRAForecast(domain, multiverse);
    }
}
