package com.jaxzin.iraf.forecast.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Date: Feb 21, 2006
 * Time: 8:26:59 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class JSliderBuilder {

    public static class Info {
        private final JPanel panel;
        private final JSlider slider;
        private final NumberFormat format;
        private final NumberFormatter formatter;

        public Info(JPanel panel, JSlider slider, NumberFormat format, NumberFormatter formatter) {
            this.panel = panel;
            this.slider = slider;
            this.format = format;
            this.formatter = formatter;
        }

        public JPanel getPanel() {
            return panel;
        }

        public JSlider getSlider() {
            return slider;
        }

        public NumberFormat getFormat() {
            return format;
        }

        public NumberFormatter getFormatter() {
            return formatter;
        }

        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("Info");
            sb.append("{panel=").append(panel);
            sb.append(", slider=").append(slider);
            sb.append(", format=").append(format);
            sb.append(", formatter=").append(formatter);
            sb.append('}');
            return sb.toString();
        }
    }

    public interface Setter {
        void setValue(int value);
    }

    public static Info buildJSliderPanel(
            final String label,
            final int minimum,
            final int maximum,
            final int start,
            final String toolTipText,
            final Setter setter
            ) {
        return buildJSliderPanel(label, minimum, maximum, start, toolTipText, setter, 4, 20);
    }
    public static Info buildJSliderPanel(
        final String label,
        final int minimum,
        final int maximum,
        final int start,
        final String toolTipText,
        final Setter setter,
        final int majorTickCount,
        final int minorTickCount
        ) {

        // Create the integer text field
        final NumberFormat rfFormat = NumberFormat.getNumberInstance();
        rfFormat.setMaximumFractionDigits(0);
        rfFormat.setMinimumFractionDigits(0);
        final NumberFormatter rfFormatter = new NumberFormatter(rfFormat);
        rfFormatter.setMinimum(minimum);
        rfFormatter.setMaximum(maximum);
        final JFormattedTextField retirementFactorField = new JFormattedTextField(rfFormatter);
        retirementFactorField.setValue(start);
        retirementFactorField.setColumns(2);
        // Create actions to handle valid/invalid data entry (on enter key)
        retirementFactorField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
        retirementFactorField.getActionMap()
                .put(
                        "check",
                        new AbstractAction() {
                            public void actionPerformed(ActionEvent actionEvent) {
                                if(!retirementFactorField.isEditValid()) {
                                    Toolkit.getDefaultToolkit().beep();
                                    retirementFactorField.selectAll();
                                } else try {
                                    retirementFactorField.commitEdit();
                                } catch (ParseException ignored) {
                                }
                            }
                        }
                );


        final JSlider retFactorSlider = new JSlider(minimum, maximum, start);
        retFactorSlider.setPaintLabels(true);
        retFactorSlider.setPaintTicks(true);
        retFactorSlider.setMajorTickSpacing((maximum - minimum) / majorTickCount);
        retFactorSlider.setMinorTickSpacing((maximum - minimum) / minorTickCount);
        retFactorSlider.setToolTipText(toolTipText);
        retFactorSlider.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent changeEvent) {
                        final JSlider slider = (JSlider)changeEvent.getSource();
                        setter.setValue(slider.getValue());

                        // Update the text field, only set the text, as
                        if(!slider.getValueIsAdjusting()) {
                            retirementFactorField.setValue(slider.getValue());
                        } else {
                            retirementFactorField.setText(rfFormat.format(slider.getValue()));
                        }
                    }
                }
        );

        // Now add listener to bind field to slider
        retirementFactorField.addPropertyChangeListener("value",
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        final Number value = (Number)e.getNewValue();
                        retFactorSlider.setValue(value.intValue());
                    }
                }
        );


        final JPanel retFactorPanel = new JPanel();
        final JLabel retFactorLabel = new JLabel(label);
        retFactorLabel.setLabelFor(retFactorSlider);
        retFactorPanel.add(retFactorLabel);
        retFactorPanel.add(retirementFactorField);
        retFactorPanel.add(retFactorSlider);

        return new Info(retFactorPanel, retFactorSlider, rfFormat, rfFormatter);
    }
}
