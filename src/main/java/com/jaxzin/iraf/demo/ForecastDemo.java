package com.jaxzin.iraf.demo;

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
 * Date: Feb 13, 2006
 * Time: 8:16:46 AM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class ForecastDemo {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }

        final ForecastDomain domain = buildInitialForecastDomain();
        final Forecaster forecaster = new IRAForecaster();
        final JForecaster forecastComponent = new JForecaster(forecaster, domain);
        final JFrame frame = new JFrame("Demo");
        frame.getContentPane().add(forecastComponent);
        ForecastDemo.setupJFrame(frame);

    }

    private static ForecastDomain buildInitialForecastDomain() {
        ForecastDomain domain = new IRAForecastDomain();

        // Locate the simulation start point
        domain.setInitialInvestment(Quantity.<Money>valueOf("45000 USD"));

        // Setup the data about me
        domain.setInitialAge(28);
        domain.setRetirementAge(59);
        domain.setLifespan(90);

        // Setup the data about my job
        domain.setInitialSalary(Quantity.<Money>valueOf("95000 USD"));
        domain.setBonus(Quantity.<Dimensionless>valueOf("14 %"));
        domain.setRaise(Quantity.<Dimensionless>valueOf("5.5 %"));
        domain.setPaychecksPerYear(24);

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


    private static void setupJFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }

}
