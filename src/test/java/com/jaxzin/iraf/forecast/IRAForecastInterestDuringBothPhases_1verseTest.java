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

import com.jaxzin.common.finance.growthsim.SimpleIssueStats;
import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Quantity;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: Feb 13, 2006
 * Time: 10:07:09 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class IRAForecastInterestDuringBothPhases_1verseTest extends AbstractIRAForecastTest {
    protected CaseData getCaseData() {
        final CaseData data = new CaseData();

        data.domain = new IRAForecastDomain();

        // Locate the simulation start point
        data.domain.setInitialInvestment(Quantity.<Money>valueOf("1 USD"));

        // Setup the data about me
        data.domain.setInitialAge(30);
        data.domain.setRetirementAge(35);
        data.domain.setLifespan(40);

        // Setup the data about my job
        data.domain.setInitialSalary(Quantity.<Money>valueOf("0 USD"));
        data.domain.setBonus(Quantity.<Dimensionless>valueOf("0 %"));
        data.domain.setRaise(Quantity.<Dimensionless>valueOf("0 %"));
        data.domain.setPaychecksPerYear(1);

        // Setup the data about contributions to IRA
        data.domain.setContribution(Quantity.<Dimensionless>valueOf("0 %"));
        data.domain.setEmployerMatch(Quantity.<Dimensionless>valueOf("0 %"));

        // Setup data about the market
        data.domain.setEmploymentPhaseStats(new SimpleIssueStats(Quantity.<Dimensionless>valueOf("10 %"), Quantity.<Dimensionless>valueOf("12 %")));
        data.domain.setRetirementPhaseStats(new SimpleIssueStats(Quantity.<Dimensionless>valueOf("5 %"), Quantity.<Dimensionless>valueOf("1 %")));
        data.domain.setRiskFreeStats(new SimpleIssueStats(Quantity.<Dimensionless>valueOf("3.5 %"), Quantity.<Dimensionless>valueOf("0.25 %")));
        data.domain.setAdjustForInflation(false);

        // Setup data about retirement
        data.domain.setRetirementFactor(Quantity.<Dimensionless>valueOf("0 %"));

        data.domain.setMultiverseSize(1);

        List<List<Money>> forecastData = new ArrayList<List<Money>>();
        List<Money> universeData = new ArrayList<Money>();
        universeData.add(Quantity.<Money>valueOf("1.00 USD"));
        universeData.add(Quantity.<Money>valueOf("1.10 USD"));
        universeData.add(Quantity.<Money>valueOf("1.21 USD"));
        universeData.add(Quantity.<Money>valueOf("1.331 USD"));
        universeData.add(Quantity.<Money>valueOf("1.4641 USD"));
        universeData.add(Quantity.<Money>valueOf("1.61051 USD"));
        universeData.add(Quantity.<Money>valueOf("1.6910355 USD"));
        universeData.add(Quantity.<Money>valueOf("1.775587275 USD"));
        universeData.add(Quantity.<Money>valueOf("1.86436663875 USD"));
        universeData.add(Quantity.<Money>valueOf("1.9575849706875013 USD"));
        universeData.add(Quantity.<Money>valueOf("2.0554642192218764 USD"));
        forecastData.add(universeData);

        data.expected = new IRAForecast(data.domain,  forecastData);

        return data;
    }
}
