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
package com.jaxzin.iraf.forecast.swing;

import com.jaxzin.common.finance.forecast.Forecast;
import com.jaxzin.common.finance.forecast.ForecastDomain;
import com.jaxzin.common.finance.forecast.ForecastException;
import com.jaxzin.common.finance.forecast.Forecaster;
import org.jfree.chart.*;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.Block;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.ColumnArrangement;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;
import org.jscience.economics.money.Money;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/**
 * Date: Feb 15, 2006
 * Time: 9:18:15 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@SuppressWarnings({"DeserializableClassInSecureContext", "SerializableClassInSecureContext", "ClassTooDeepInInheritanceTree"})
public class JForecaster extends JComponent {

    private Forecaster forecaster;
    private ForecastDomain domain;

    @SuppressWarnings({"InstanceVariableMayNotBeInitialized"})
    private LogarithmicAxis logAxis;
    @SuppressWarnings({"InstanceVariableMayNotBeInitialized"})
    private NumberAxis linearAxis;
    @SuppressWarnings({"InstanceVariableOfConcreteClass"})
    private JForecasterControl controlPanel;
    private static final int TOTAL_PERCENTILES = 100;


    @SuppressWarnings({"ParameterHidesMemberVariable"})
    public JForecaster(final Forecaster forecaster, final ForecastDomain domain) {
        this.forecaster = forecaster;
        this.domain = domain;
        initialize();
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public Forecaster getForecaster() {
        return forecaster;
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public ForecastDomain getDomain() {
        return domain;
    }

    @SuppressWarnings({"FieldRepeatedlyAccessedInMethod"})
    private void initialize() {
//        final GridBagLayout gridbag = new GridBagLayout();
        setLayout(new GridBagLayout());//new BoxLayout(this, BoxLayout.PAGE_AXIS));

        controlPanel = new JForecasterControl(domain);

        final JFreeChart chart = createJFreeChart();
        final JPanel chartPanel = new ChartPanel(chart, true);
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
//        gridbag.setConstraints(chartPanel, c);
        add(chartPanel, c);

        // todo: change to changelistener of JControlPanel
        domain.addPropertyChangeListener(
                new PropertyChangeListener() {
                    public void propertyChange(final PropertyChangeEvent e) {
                        chart.getXYPlot().setDataset(createDataset());
                        //noinspection CallToStringEquals
                        if("adjustForInflation".equals(e.getPropertyName())) {
                            final String label;
                            if(domain.isAdjustForInflation()) {
                                label = "Account Value (Adjusted to Today's Value)";
                            } else {
                                label = "Account Value";
                            }
                            logAxis.setLabel(label);
                            linearAxis.setLabel(label);
                        }
                    }
                }
        );
        controlPanel.addPropertyChangeListener("logScale",
                new PropertyChangeListener() {
                    public void propertyChange(final PropertyChangeEvent e) {
                        if(controlPanel.isLogScale()) {
                            chart.getXYPlot().setRangeAxis(logAxis);
                        } else {
                            chart.getXYPlot().setRangeAxis(linearAxis);
                        }
                    }
                }
        );

        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.SOUTH;
//        gridbag.setConstraints(controlPanel, c1);
        add(controlPanel, c);
    }

    protected JFreeChart createJFreeChart() {
        final JFreeChart chart =
                ChartFactory.createTimeSeriesChart(
                        "401(k) Forecast",
                        "Year",
                        "",
                        createDataset(),
                        true,
                        true,
                        true
                );
        customizeChart(chart);
        return chart;
    }

    @SuppressWarnings({"FieldRepeatedlyAccessedInMethod"})
    private void customizeChart(final JFreeChart chart) {
        // Set the transparency of the histogram bars
//        chart.getXYPlot().setForegroundAlpha(0.5f);
        // Lock the y-axis to 0.0->0.5

        // Customize the y-logAxis
        logAxis = new LogarithmicAxis("Account Value");
        logAxis.setAutoRange(true);
        logAxis.setAllowNegativesFlag(true);
        logAxis.setNumberFormatOverride(NumberFormat.getCurrencyInstance());

        linearAxis = new NumberAxis("Account Value");
        linearAxis.setAutoRange(true);
        linearAxis.setNumberFormatOverride(NumberFormat.getCurrencyInstance());

        //noinspection ConditionalExpression
        chart.getXYPlot().setRangeAxis(controlPanel.isLogScale() ? logAxis : linearAxis);

        // Customize the legend (add title, reverse order, attach to right side)
        final BlockContainer legendWrap = new BlockContainer();
        final Block title = new LabelBlock("Percentiles");
        legendWrap.setArrangement(new ColumnArrangement());
        legendWrap.add(title);
        final LegendTitle legendTitle =
                new LegendTitle(new ReversedLegendItemSource(chart.getXYPlot()), new ColumnArrangement(), new ColumnArrangement());
        legendWrap.add(legendTitle);
        chart.getLegend().setWrapper(legendWrap);
        chart.getLegend().setPosition(RectangleEdge.RIGHT);

        // Customize the format of the tooltips
        chart.getXYPlot().getRenderer().setBaseToolTipGenerator(
                new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                        new SimpleDateFormat("yyyy"),
                        NumberFormat.getCurrencyInstance()
                )
        );
    }

    @SuppressWarnings({"MethodWithMultipleLoops"})
    protected XYDataset createDataset() {
        final TimeSeriesCollection tsc = new TimeSeriesCollection();

//        long start = System.currentTimeMillis();
        Forecast forecastData = null;
        try {
//            System.out.println("Generating dataset; initial salary is <"+domain.getInitialSalary()+">");
            forecastData = forecaster.forecast(domain);
        } catch (ForecastException e) {
            e.printStackTrace();
        }

        int count = 1;
        assert forecastData != null;
        final int total = forecastData.getForecastData().size() + 1;
        //noinspection ForLoopThatDoesntUseLoopVariable
        for (Iterator i = forecastData.getForecastData().iterator(); i.hasNext(); count++) {
            final List<Money> universeData = (List<Money>) i.next();
            //noinspection ObjectAllocationInLoop,UnqualifiedStaticUsage
            final TimeSeries ts = new TimeSeries(new StringBuilder().append(String.valueOf((count * TOTAL_PERCENTILES) / total)).append("th").toString(), Year.class);
            for(Money money : universeData) {
                if(ts.getItemCount() == 0) {
                    //noinspection ObjectAllocationInLoop
                    ts.add(
                            RegularTimePeriod.createInstance(
                                    Year.class,
                                    new Date(),
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

        }
//        System.out.println(System.currentTimeMillis()-start);

        return tsc;
    }


    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(" :: ");
        sb.append("JForecaster");
        sb.append("{forecaster=").append(forecaster);
        sb.append(", domain=").append(domain);
        sb.append(", logAxis=").append(logAxis);
        sb.append(", linearAxis=").append(linearAxis);
        sb.append(", controlPanel=").append(controlPanel);
        //noinspection MagicCharacter
        sb.append('}');
        return sb.toString();
    }

    @SuppressWarnings({"ClassWithoutToString"})
    private static class ReversedLegendItemSource implements LegendItemSource {

        private LegendItemSource source;

        @SuppressWarnings({"ParameterHidesMemberVariable"})
        ReversedLegendItemSource(final LegendItemSource source) {
            this.source = source;
        }

        public LegendItemCollection getLegendItems() {
            final LegendItemCollection forward = source.getLegendItems();
            final LegendItemCollection backward = new LegendItemCollection();
            for (int i = forward.getItemCount() - 1; i >= 0; i--) {
                backward.add(forward.get(i));
            }
            return backward;
        }
    }

}
