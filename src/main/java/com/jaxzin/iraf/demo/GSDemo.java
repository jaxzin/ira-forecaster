package com.jaxzin.iraf.demo;

import com.jaxzin.common.finance.growthsim.GSDomainImpl;
import com.jaxzin.common.finance.growthsim.GrowthSimulator;
import com.jaxzin.common.finance.growthsim.SimulationException;
import com.jaxzin.common.finance.growthsim.StandardGrowthSimulator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.date.DateUtilities;
import org.jscience.economics.money.Money;
import org.jscience.physics.quantities.Dimensionless;
import org.jscience.physics.quantities.Quantity;

import javax.swing.*;
import java.awt.*;
import java.util.TimeZone;

/**
 * Date: Feb 13, 2006
 * Time: 8:16:46 AM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class GSDemo {

    public static void main(String[] args) {
        final JFreeChart chart =
                ChartFactory.createTimeSeriesChart(
                        "Timeseries",
                        "",
                        "",
                        createData(),
                        false,
                        true,
                        true
                );

        // Customize the chart
        customizeChart(chart);

        final JPanel panel = new ChartPanel(chart, true);
        final JFrame frame = new JFrame("Demo");
        frame.getContentPane().add(panel);
        setupJFrame(frame);
    }

    private static XYDataset createData() {
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        GrowthSimulator sim = new StandardGrowthSimulator();
        sim.setDomain(new GSDomainImpl());

        // Locate the simulation start point
        sim.getDomain().setInitialInvestment(Quantity.<Money>valueOf("45000 USD"));

        // Setup the data about me
        sim.getDomain().setInitialAge(28);
        sim.getDomain().setRetirementAge(60);
        sim.getDomain().setLifespan(90);

        // Setup the data about my job
        sim.getDomain().setInitialSalary(Quantity.<Money>valueOf("95000 USD"));
        sim.getDomain().setBonus(Quantity.<Dimensionless>valueOf("14 %"));
        sim.getDomain().setRaise(Quantity.<Dimensionless>valueOf("5.5 %"));
        sim.getDomain().setPaychecksPerYear(1);

        // Setup the data about contributions to IRA
        sim.getDomain().setContribution(Quantity.<Dimensionless>valueOf("4 %"));
        sim.getDomain().setEmployerMatch(Quantity.<Dimensionless>valueOf("75 %"));

        // Setup data about the market
        sim.getDomain().setRor1(Quantity.<Dimensionless>valueOf("10 %"));
        sim.getDomain().setRor2(Quantity.<Dimensionless>valueOf("5 %"));
        sim.getDomain().setRiskFreeRate(Quantity.<Dimensionless>valueOf("3 %"));
        sim.getDomain().setAdjustForInflation(false);

        // Setup data about retirement
        sim.getDomain().setRetirementFactor(Quantity.<Dimensionless>valueOf("25 %"));

        java.util.List<Money> moneys = null;
        try {
            moneys = sim.simulate();
        } catch (SimulationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        TimeSeries ts = new TimeSeries("", Year.class);
        for(Money money : moneys) {
            if(ts.getItemCount() == 0) {
                ts.add(
                        RegularTimePeriod.createInstance(
                                Year.class,
                                DateUtilities.createDate(2006,1,1),
                                TimeZone.getDefault()
                        ),
                        money.doubleValue()
                );
            } else {
                ts.add(
                        ts.getNextTimePeriod(),
                        money.doubleValue()
                );
            }
        }

        tsc.addSeries(ts);

        return tsc;
    }

    private static void setupJFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setVisible(true);
    }

    private static void customizeChart(JFreeChart chart) {
        // Set the transparency of the histogram bars
        chart.getXYPlot().setForegroundAlpha(0.5f);
        // Lock the y-axis to 0.0->0.5
//        ValueAxis axis = new NumberAxis("Probability");
//        axis.setAutoRange(false);
//        axis.setRange(new Range(0, 0.5));
//        chart.getXYPlot().setRangeAxis(axis);
//        // Lock the x-axis to -6.0->6.0
//        ValueAxis domainAxis = new NumberAxis("Value");
//        domainAxis.setAutoRange(false);
//        domainAxis.setRange(new Range(-6.0, 6.0));
//        chart.getXYPlot().setDomainAxis(domainAxis);
    }
}
