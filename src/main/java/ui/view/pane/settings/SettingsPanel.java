package ui.view.pane.settings;

import ui.view.component.HeaderLabel;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.LIGHT_GRAY;
import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;

class SettingsPanel extends JPanel {
    SettingsPanel(SettingsComponent settingsComponent) {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = FIRST_LINE_START;
        constraints.insets = new Insets(15, 15, 10 ,15);

        add(new HeaderLabel(settingsComponent.title()), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = FIRST_LINE_START;
        constraints.insets = new Insets(0, 15, 5 ,15);
        add(new JLabel(settingsComponent.description()), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = FIRST_LINE_START;
        constraints.insets = new Insets(0, 15, 0 ,15);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(settingsComponent.component(), constraints);

        JSeparator separator = new JSeparator();
        separator.setForeground(LIGHT_GRAY);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = FIRST_LINE_START;
        constraints.insets = new Insets(15, 0, 0 ,0);
        constraints.fill = HORIZONTAL;
        add(separator, constraints);
    }
}
