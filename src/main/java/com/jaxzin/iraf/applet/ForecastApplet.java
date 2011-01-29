package com.jaxzin.iraf.applet;

import com.jaxzin.common.finance.forecast.ForecastDomain;
import com.jaxzin.common.finance.forecast.Forecaster;
import com.jaxzin.common.finance.growthsim.SimpleIssueStats;
import com.jaxzin.iraf.forecast.IRAForecastDomain;
import com.jaxzin.iraf.forecast.IRAForecaster;
import com.jaxzin.iraf.forecast.swing.JForecaster;
import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Quantity;

import javax.swing.*;

/**
 * Date: Feb 21, 2006
 * Time: 9:51:25 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class ForecastApplet extends JApplet {
    public void init() {
        super.init();

        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }


        final ForecastDomain domain = buildInitialForecastDomain();
        final Forecaster forecaster = new IRAForecaster();
        final JForecaster forecastComponent = new JForecaster(forecaster, domain);
        getContentPane().add(forecastComponent);
    }

    private static ForecastDomain buildInitialForecastDomain() {
        ForecastDomain domain = new IRAForecastDomain();

        // Locate the simulation start point
        domain.setInitialInvestment(Quantity.<Money>valueOf("10000 USD"));

        // Setup the data about me
        domain.setInitialAge(23);
        domain.setRetirementAge(59);
        domain.setLifespan(90);

        // Setup the data about my job
        domain.setInitialSalary(Quantity.<Money>valueOf("50000 USD"));
        domain.setBonus(Quantity.<Dimensionless>valueOf("5 %"));
        domain.setRaise(Quantity.<Dimensionless>valueOf("6 %"));
        domain.setPaychecksPerYear(52);

        // Setup the data about contributions to IRA
        domain.setContribution(Quantity.<Dimensionless>valueOf("4 %"));
        domain.setEmployerMatch(Quantity.<Dimensionless>valueOf("75 %"));

        // Setup data about the market
        domain.setEmploymentPhaseStats(new SimpleIssueStats(Quantity.<Dimensionless>valueOf("10 %"), Quantity.<Dimensionless>valueOf("12 %")));
        domain.setRetirementPhaseStats(new SimpleIssueStats(Quantity.<Dimensionless>valueOf("5 %"), Quantity.<Dimensionless>valueOf("1 %")));
        domain.setRiskFreeStats(new SimpleIssueStats(Quantity.<Dimensionless>valueOf("3.5 %"), Quantity.<Dimensionless>valueOf("0.25 %")));
        domain.setAdjustForInflation(false);

        // Setup data about retirement
        domain.setRetirementFactor(Quantity.<Dimensionless>valueOf("25 %"));

        domain.setMultiverseSize(9);
        return domain;
    }
}
