package com.jaxzin.iraf.demo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

/**
 * Date: Feb 9, 2006
 * Time: 10:32:11 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class Main {
    private static final int COUNT = 1000;
    private static final int DELAY = 100;

    public static void main(String[] args) {
        final JFreeChart chart =
                ChartFactory.createHistogram(
                        "Probability of Values",
                        "Value",
                        "Probability",
                        createData(COUNT),
                        PlotOrientation.VERTICAL,
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

        java.util.Timer t = new java.util.Timer();
        t.schedule(new DataUpdater(chart), DELAY, DELAY);
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
        ValueAxis axis = new NumberAxis("Probability");
        axis.setAutoRange(false);
        axis.setRange(new Range(0, 0.5));
        chart.getXYPlot().setRangeAxis(axis);
        // Lock the x-axis to -6.0->6.0
        ValueAxis domainAxis = new NumberAxis("Value");
        domainAxis.setAutoRange(false);
        domainAxis.setRange(new Range(-6.0, 6.0));
        chart.getXYPlot().setDomainAxis(domainAxis);
    }

    private static IntervalXYDataset createData(final int count) {
        final HistogramDataset data = new HistogramDataset();
        data.setType(HistogramType.SCALE_AREA_TO_1);
        data.addSeries(
                "freq",
                createNormalDist(count, 0),
                (int)Math.round(Math.sqrt(count))
        );
        data.addSeries(
                "rel_freq",
                createNormalDist(count, 2),
                (int)Math.round(Math.sqrt(count))
        );
        data.addSeries(
                "area",
                createNormalDist(count, -2),
                (int)Math.round(Math.sqrt(count))
        );
        return data;
    }

    private static double[] createNormalDist(final int count, final double offset) {
        final double[] data = new double[count];
        final Random rand = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextGaussian() + offset;
        }
        return data;
    }

    private static class DataUpdater extends TimerTask {

        JFreeChart chart;
        int count = COUNT << 1;

        DataUpdater(JFreeChart chart) {
            this.chart = chart;
        }

        public void run() {
            chart.getXYPlot().setDataset(createData(count));
            if(count < 1000)
                count <<= 1;
        }
    }
}
