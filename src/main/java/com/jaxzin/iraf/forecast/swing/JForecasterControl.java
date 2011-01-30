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

import com.jaxzin.common.finance.forecast.ForecastDomain;
import com.jaxzin.common.finance.growthsim.IssueStats;
import com.jaxzin.common.finance.growthsim.SimpleIssueStats;
import org.jscience.economics.money.Currency;
import org.jscience.physics.quantities.Quantity;
import org.jscience.physics.units.NonSI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.text.NumberFormat;

/**
 * Date: Feb 16, 2006
 * Time: 7:38:12 AM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class JForecasterControl extends JPanel {

    private ForecastDomain domain;
    private boolean logScale = true;

    public JForecasterControl(ForecastDomain domain) {
        this.domain = domain;
        initialize();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//new GridLayout(2,2));

        final JTabbedPane tabs = new JTabbedPane();
        add(tabs);

        tabs.addTab("Personal Info", createPersonalInfoPanel());
        tabs.addTab("Job Info", createJobInfoPanel());
        tabs.addTab("401(k) Info", create401kInfoPanel());
        tabs.addTab("Market Info", createMarketInfoPanel());
        tabs.addTab("Chart Info", createChartInfoPanel());
    }

    private Component createPersonalInfoPanel() {
        final JPanel personalInfo = new JPanel();
        personalInfo.setLayout(new BoxLayout(personalInfo, BoxLayout.PAGE_AXIS));
        personalInfo.setBorder(BorderFactory.createTitledBorder("Personal Info"));

        final JSpinner initialAgeSpinner = new JSpinner(new SpinnerNumberModel(domain.getInitialAge(), 0, domain.getLifespan(), 1));
        final JPanel iaPanel = new JPanel();
        final JLabel initialAgeLabel = new JLabel("Current Age");
        initialAgeLabel.setLabelFor(initialAgeSpinner);
        iaPanel.add(initialAgeLabel);
        iaPanel.add(initialAgeSpinner);

        final JSpinner lifespanSpinner = new JSpinner(new SpinnerNumberModel(domain.getLifespan(), domain.getInitialAge(), 999, 1));
        final JPanel lsPanel = new JPanel();
        final JLabel lifespanLabel = new JLabel("Expected Lifespan");
        lifespanLabel.setLabelFor(lifespanSpinner);
        lsPanel.add(lifespanLabel);
        lsPanel.add(lifespanSpinner);

        final JPanel spinnerPanel = new JPanel();
        spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.LINE_AXIS));
        spinnerPanel.add(iaPanel);
        spinnerPanel.add(lsPanel);

        personalInfo.add(spinnerPanel);


        final JSliderBuilder.Info raInfo =
                JSliderBuilder.buildJSliderPanel(
                        "Retirement Age",
                        domain.getInitialAge(),
                        domain.getLifespan(),
                        domain.getRetirementAge(),
                        "The age at which you expect to retire.",
                        new JSliderBuilder.Setter() {
                            public void setValue(int value) {
                                domain.setRetirementAge(value);
                            }
                        }
                );
        final JSlider retirementAgeSlider = raInfo.getSlider();

        initialAgeSpinner.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent changeEvent) {
                        int initialAge = (Integer) ((JSpinner)changeEvent.getSource()).getValue();
                        retirementAgeSlider.setMinimum(initialAge);
                        raInfo.getFormatter().setMinimum(initialAge);
                        ((SpinnerNumberModel)lifespanSpinner.getModel()).setMinimum(initialAge);
                        retirementAgeSlider.setMajorTickSpacing((retirementAgeSlider.getMaximum()-retirementAgeSlider.getMinimum())/4);
                        retirementAgeSlider.setMinorTickSpacing(Math.max(1,retirementAgeSlider.getMaximum()-retirementAgeSlider.getMinimum())/20);
                        retirementAgeSlider.setLabelTable(
                                retirementAgeSlider.createStandardLabels(
                                        retirementAgeSlider.getMajorTickSpacing(),
                                        retirementAgeSlider.getMinimum()
                                )
                        );


                        domain.setInitialAge(initialAge);
                    }
                }
        );

        lifespanSpinner.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent changeEvent) {
                        int lifespan = (Integer) ((JSpinner)changeEvent.getSource()).getValue();
                        retirementAgeSlider.setMaximum(lifespan);
                        raInfo.getFormatter().setMaximum(lifespan);
                        ((SpinnerNumberModel)initialAgeSpinner.getModel()).setMaximum(lifespan);
                        retirementAgeSlider.setMajorTickSpacing((retirementAgeSlider.getMaximum()-retirementAgeSlider.getMinimum())/4);
                        retirementAgeSlider.setMinorTickSpacing(Math.max(1,retirementAgeSlider.getMaximum()-retirementAgeSlider.getMinimum())/20);
                        retirementAgeSlider.setLabelTable(
                                retirementAgeSlider.createStandardLabels(
                                        retirementAgeSlider.getMajorTickSpacing(),
                                        retirementAgeSlider.getMinimum()
                                )
                        );

                        domain.setLifespan(lifespan);
                    }
                }
        );

        personalInfo.add(raInfo.getPanel());


        final JSliderBuilder.Info rfInfo =
                JSliderBuilder.buildJSliderPanel(
                        "Annual Withdrawal (% of Salary)",
                        0,
                        100,
                        (int) domain.getRetirementFactor().getAmount(),
                        "The percentage of your base salary that you expect to withdraw from your account annually during retirement.",
                        new JSliderBuilder.Setter() {
                            public void setValue(int value) {
                                domain.setRetirementFactor(Quantity.valueOf(value, NonSI.PERCENT));
                            }
                        }
                );

        personalInfo.add(rfInfo.getPanel());


        return personalInfo;
    }

    private Component createJobInfoPanel() {
        final JPanel jobInfo = new JPanel();
        jobInfo.setLayout(new BoxLayout(jobInfo, BoxLayout.PAGE_AXIS));
        jobInfo.setBorder(BorderFactory.createTitledBorder("Job Info"));

        final JSpinner initialSalary =
                new JSpinner(
                        new SpinnerNumberModel(
                                domain.getInitialSalary().intValue(),
                                0,
                                1000000,
                                1000
                        )
                );
        // Format the JSpinner as currency
//        ResourceBundle resourcebundle = LocaleData.getLocaleElements(Locale.getDefault());
//        String as[] = resourcebundle.getStringArray("NumberPatterns");
//        initialSalary.setEditor(new JSpinner.NumberEditor(initialSalary, as[1]));
        initialSalary.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        int initialSalary = (Integer) ((JSpinner)e.getSource()).getValue();
//                        System.out.println("salary changed to: "+initialSalary);
                        domain.setInitialSalary(Quantity.valueOf(initialSalary, Currency.USD));
                    }
                }
        );

        final JPanel isPanel = new JPanel();
        final JLabel isLabel = new JLabel("Current Salary (USD)");
        isLabel.setLabelFor(initialSalary);
        isPanel.add(isLabel);
        isPanel.add(initialSalary);

        jobInfo.add(isPanel);

        final JSliderBuilder.Info bonusInfo =
                JSliderBuilder.buildJSliderPanel(
                        "Annual Bonus (%)",
                        0,
                        100,
                        (int) domain.getBonus().getAmount(),
                        "The percentage of your base salary that you receive as a bonus annually.",
                        new JSliderBuilder.Setter() {
                            public void setValue(int value) {
                                domain.setBonus(Quantity.valueOf(value, NonSI.PERCENT));
                            }
                        }
                );

        jobInfo.add(bonusInfo.getPanel());

        final JSliderBuilder.Info raiseInfo =
                JSliderBuilder.buildJSliderPanel(
                        "Annual Raise (%)",
                        0,
                        100,
                        (int) domain.getRaise().getAmount(),
                        "The percentage of your base salary that you receive as a raise annually.",
                        new JSliderBuilder.Setter() {
                            public void setValue(int value) {
                                domain.setRaise(Quantity.valueOf(value, NonSI.PERCENT));
                            }
                        }
                );

        jobInfo.add(raiseInfo.getPanel());


        final JComboBox paycheckList =
                new JComboBox(
                        new Object[] {
                                "Weekly",
                                "Semi-weekly",
                                "Bi-monthly",
                                "Monthly"
                        }
                );
//        paycheckList.setVisibleRowCount(1);
        paycheckList.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String style = (String)((JComboBox)e.getSource()).getSelectedItem();
                        int countPerYear = getCountPerYear(style);
                        domain.setPaychecksPerYear(countPerYear);
                    }

                    private int getCountPerYear(String style) {
                        if("Weekly".equals(style))
                            return 52;
                        else if("Semi-weekly".equals(style))
                            return 26;
                        else if("Bi-monthly".equals(style))
                            return 24;
                        else if("Monthly".equals(style))
                            return 12;
                        else
                            return 1;
                    }
                }
        );

        final JPanel paychecksPanel = new JPanel();
        final JLabel paychecksLabel = new JLabel("Paycheck Period");
        paychecksLabel.setLabelFor(paycheckList);
        paychecksPanel.add(paychecksLabel);
        paychecksPanel.add(paycheckList);

        jobInfo.add(paychecksPanel);

        return jobInfo;
    }

    private Component create401kInfoPanel() {
        final JPanel iraInfo = new JPanel();
        iraInfo.setLayout(new BoxLayout(iraInfo, BoxLayout.PAGE_AXIS));
        iraInfo.setBorder(BorderFactory.createTitledBorder("401(k) Info"));

        final JSpinner initialInvestment =
                new JSpinner(
                        new SpinnerNumberModel(
                                domain.getInitialInvestment().intValue(),
                                0,
                                1000000,
                                1000
                        )
                );
        // Format the JSpinner as currency
//        ResourceBundle resourcebundle = LocaleData.getLocaleElements(Locale.getDefault());
//        String as[] = resourcebundle.getStringArray("NumberPatterns");
//        initialInvestment.setEditor(new JSpinner.NumberEditor(initialInvestment, as[1]));
        initialInvestment.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(final ChangeEvent e) {
                        final int initialInvestment = (Integer) ((JSpinner)e.getSource()).getValue();
//                        System.out.println("salary changed to: "+initialInvestment);
                        domain.setInitialInvestment(Quantity.valueOf(initialInvestment, Currency.USD));
                    }
                }
        );

        final JPanel iiPanel = new JPanel();
        final JLabel iiLabel = new JLabel("Current Account Value (USD)");
        iiLabel.setLabelFor(initialInvestment);
        iiPanel.add(iiLabel);
        iiPanel.add(initialInvestment);

        iraInfo.add(iiPanel);


        final JSliderBuilder.Info contribInfo =
                JSliderBuilder.buildJSliderPanel(
                        "Contribution (%)",
                        0,
                        15,
                        (int) domain.getContribution().getAmount(),
                        "The percentage of your base salary that you will contribute to your 401(k) annually.",
                        new JSliderBuilder.Setter() {
                            public void setValue(int value) {
                                domain.setContribution(Quantity.valueOf(value, NonSI.PERCENT));
                            }
                        },
                        3,
                        15
                );

        iraInfo.add(contribInfo.getPanel());


        final JSliderBuilder.Info emInfo =
                JSliderBuilder.buildJSliderPanel(
                        "Employer Match (%)",
                        0,
                        100,
                        (int) domain.getEmployerMatch().getAmount(),
                        "The percentage of your annual 401(k) contribution that your employer will contribute a matching amount.",
                        new JSliderBuilder.Setter() {
                            public void setValue(int value) {
                                domain.setEmployerMatch(Quantity.valueOf(value, NonSI.PERCENT));
                            }
                        }
                );

        iraInfo.add(emInfo.getPanel());

        return iraInfo;
    }


    private static NumberFormat percentFmt = NumberFormat.getPercentInstance();
    static {
        percentFmt.setMaximumFractionDigits(2);
        percentFmt.setMinimumFractionDigits(2);
    }

    private Component createMarketInfoPanel() {
        final JPanel marketInfo = new JPanel();
        marketInfo.setLayout(new BoxLayout(marketInfo, BoxLayout.PAGE_AXIS));
        marketInfo.setBorder(BorderFactory.createTitledBorder("Market Info"));


        final int currentEPSValue = (int)(Math.round(domain.getEmploymentPhaseStats().getMeanReturn().getAmount() - 5.0)*100.0/(10.0 - 5.0));

        final JPanel emValuesPanel = new JPanel();
        emValuesPanel.setLayout(new GridLayout(2,2,5,0));
        emValuesPanel.setBorder(BorderFactory.createEtchedBorder(1));
        emValuesPanel.add(new JLabel("Return:"));
        final JLabel emReturnLabel = new JLabel(percentFmt.format(domain.getEmploymentPhaseStats().getMeanReturn().doubleValue()));
        emValuesPanel.add(emReturnLabel);
        emValuesPanel.add(new JLabel("Risk:"));
        final JLabel emRiskLabel = new JLabel(percentFmt.format((11.0 * (currentEPSValue/100.0) + 1.0)/100.0));
        emValuesPanel.add(emRiskLabel);

        final JSlider emSlider = new JSlider(0, 100, currentEPSValue);
        emSlider.setPaintLabels(true);
        emSlider.setPaintTicks(true);
        emSlider.setMajorTickSpacing(50);
        emSlider.setMinimumSize(new Dimension(275,50));
        emSlider.setPreferredSize(new Dimension(275,50));
        Dictionary emLabels = new Hashtable();
        emLabels.put(new Integer(0), new JLabel("Conservative"));
        emLabels.put(new Integer(50), new JLabel("Moderate"));
        emLabels.put(new Integer(100), new JLabel("Aggressive"));
        emSlider.setLabelTable(emLabels);
        emSlider.setToolTipText("The risk/return profile of your investments during the time period that you are employed.");
        emSlider.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent changeEvent) {
                        final JSlider slider = (JSlider)changeEvent.getSource();
                        final double factor = slider.getValue() / 100.0;
                        final double meanReturn = 5.0 * factor + 5.0;
                        final double stdDev = 11.0 * factor + 1.0;
                        final IssueStats stats =
                                new SimpleIssueStats(
                                        Quantity.valueOf(meanReturn, NonSI.PERCENT),
                                        Quantity.valueOf(stdDev, NonSI.PERCENT)
                                );
                        domain.setEmploymentPhaseStats(stats);
                        emReturnLabel.setText(percentFmt.format(stats.getMeanReturn().doubleValue()));
                        emRiskLabel.setText(percentFmt.format(stats.getStandardDeviation().doubleValue()));
                    }
                }
        );

        final JPanel emPanel = new JPanel();
        final JPanel emLabelPanel = new JPanel();
        emLabelPanel.setLayout(new BoxLayout(emLabelPanel, BoxLayout.PAGE_AXIS));
        final JLabel emLabel = new JLabel("Investment Type");
        emLabelPanel.add(emLabel);
        emLabelPanel.add(new JLabel("(During Employment)"));
        emLabel.setLabelFor(emSlider);
        emPanel.add(emLabelPanel);
        emPanel.add(Box.createRigidArea(new Dimension(20,10)));
        emPanel.add(emSlider);
        emPanel.add(emValuesPanel);

        marketInfo.add(emPanel);

        final int currentRPSValue = (int)(Math.round(domain.getRetirementPhaseStats().getMeanReturn().getAmount()-5.0)*100.0/(10.0 - 5.0));

        final JPanel retValuesPanel = new JPanel();
        retValuesPanel.setLayout(new GridLayout(2,2,5,0));
        retValuesPanel.setBorder(BorderFactory.createEtchedBorder(1));
        retValuesPanel.add(new JLabel("Return:"));
        final JLabel retReturnLabel = new JLabel(percentFmt.format((5.0 * (currentRPSValue/100.0) + 5.0)/100.0));
        retValuesPanel.add(retReturnLabel);
        retValuesPanel.add(new JLabel("Risk:"));
        final JLabel retRiskLabel = new JLabel(percentFmt.format((11.0 * (currentRPSValue/100.0) + 1.0)/100.0));
        retValuesPanel.add(retRiskLabel);

        final JSlider retSlider = new JSlider(0, 100, currentRPSValue);
        retSlider.setPaintLabels(true);
        retSlider.setPaintTicks(true);
        retSlider.setMajorTickSpacing(50);
        retSlider.setMinimumSize(new Dimension(275,50));
        retSlider.setPreferredSize(new Dimension(275,50));
        Dictionary retLabels = new Hashtable();
        retLabels.put(new Integer(0), new JLabel("Conservative"));
        retLabels.put(new Integer(50), new JLabel("Moderate"));
        retLabels.put(new Integer(100), new JLabel("Aggressive"));
        retSlider.setLabelTable(retLabels);
        retSlider.setToolTipText("The risk/return profile of your investments during the time period that you are retired.");
        retSlider.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent changeEvent) {
                        final JSlider slider = (JSlider)changeEvent.getSource();
                        final double factor = slider.getValue() / 100.0;
                        final double meanReturn = 5.0 * factor + 5.0;
                        final double stdDev = 11.0 * factor + 1.0;
                        final IssueStats stats =
                                new SimpleIssueStats(
                                        Quantity.valueOf(meanReturn, NonSI.PERCENT),
                                        Quantity.valueOf(stdDev, NonSI.PERCENT)
                                );
                        domain.setRetirementPhaseStats(stats);
                        retReturnLabel.setText(percentFmt.format(stats.getMeanReturn().doubleValue()));
                        retRiskLabel.setText(percentFmt.format(stats.getStandardDeviation().doubleValue()));
                    }
                }
        );

        final JPanel retPanel = new JPanel();
        final JPanel retLabelPanel = new JPanel();
        retLabelPanel.setLayout(new BoxLayout(retLabelPanel, BoxLayout.PAGE_AXIS));
        final JLabel retLabel = new JLabel("Investment Type");
        retLabelPanel.add(retLabel);
        retLabelPanel.add(new JLabel("(During Retirement) "));
        retLabel.setLabelFor(emSlider);
        retPanel.add(retLabelPanel);
        retPanel.add(Box.createRigidArea(new Dimension(20,10)));
        retPanel.add(retSlider);
        retPanel.add(retValuesPanel);

        marketInfo.add(retPanel);

        return marketInfo;
    }

    private Component createChartInfoPanel() {
        final JPanel chartInfo = new JPanel();
        chartInfo.setLayout(new BoxLayout(chartInfo, BoxLayout.PAGE_AXIS));
        chartInfo.setBorder(BorderFactory.createTitledBorder("Chart Info"));

        final JCheckBox inflation = new JCheckBox("Adjust for Inflation", domain.isAdjustForInflation());
        inflation.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        JCheckBox checkbox = (JCheckBox) e.getSource();
                        domain.setAdjustForInflation(checkbox.isSelected());
                    }
                }
        );

        chartInfo.add(inflation);

        final JCheckBox axisScale = new JCheckBox("Logarithmic Scale", isLogScale());
        axisScale.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        JCheckBox checkbox = (JCheckBox) e.getSource();
                        setLogScale(checkbox.isSelected());
                    }
                }
        );

        chartInfo.add(axisScale);

        return chartInfo;
    }

    public boolean isLogScale() {
        return logScale;
    }

    public void setLogScale(boolean logScale) {
        final boolean old = this.logScale;
        this.logScale = logScale;
        firePropertyChange("logScale", old, this.logScale);
    }


}
