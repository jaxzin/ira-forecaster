package com.jaxzin.iraf.demo;

import com.jaxzin.common.math.MoreMath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

/**
 * Date: Feb 12, 2006
 * Time: 9:40:53 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class CdfInv {

    public static void main(String[] args) {
        final JFreeChart chart =
                ChartFactory.createLineChart(
                        "Inverse CDF",
                        "",
                        "",
                        createData(400),
                        PlotOrientation.VERTICAL,
                        true,
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

    private static CategoryDataset createData(int count) {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        for(int i = 1; i < count; i++) {
            double in = (1.0/count) * i;
            double out = MoreMath.cdfinv(in);
            data.addValue(out, "nor", new Double(in));
        }
        return data;
    }

    private static void setupJFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setVisible(true);
    }

    private static void customizeChart(JFreeChart chart) {
        // Set the transparency of the histogram bars
        chart.getCategoryPlot().setForegroundAlpha(0.5f);

        // Lock the y-axis to 0.0->0.5
        ValueAxis axis = new NumberAxis("Value");
        axis.setAutoRange(true);
        chart.getCategoryPlot().setRangeAxis(axis);
        // Lock the x-axis to -6.0->6.0
        CategoryAxis domainAxis = new CategoryAxis("Probability");
        domainAxis.setTickLabelsVisible(false);
        chart.getCategoryPlot().setDomainAxis(domainAxis);
    }
}
