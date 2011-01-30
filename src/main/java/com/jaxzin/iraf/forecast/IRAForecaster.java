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
