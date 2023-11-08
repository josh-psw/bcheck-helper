package ui.view.pane.settings;

import settings.debug.DebugSettings;
import ui.view.component.HeaderLabel;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;

class DebugSettingsPanel extends JPanel {
    DebugSettingsPanel(DebugSettings debugSettings) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 20};
        layout.rowHeights = new int[]{0, 10, 0, 5, 0};
        setLayout(layout);

        JCheckBox loggingCheckbox = new JCheckBox("Enabled");
        loggingCheckbox.setSelected(debugSettings.logging());
        loggingCheckbox.addActionListener(e -> debugSettings.setLogging(loggingCheckbox.isSelected()));

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = FIRST_LINE_START;
        constraints.gridwidth = 3;
        JLabel headerLabel = new HeaderLabel("Logging");
        add(headerLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = FIRST_LINE_START;
        constraints.gridwidth = 3;
        constraints.weightx = 1.0;
        constraints.fill = HORIZONTAL;
        JLabel descriptionLabel = new JLabel("Use this setting to enable or disable logging");
        add(descriptionLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = FIRST_LINE_START;
        constraints.gridwidth = 3;
        add(loggingCheckbox, constraints);
    }
}
