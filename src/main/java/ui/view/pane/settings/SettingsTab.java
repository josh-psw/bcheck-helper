package ui.view.pane.settings;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;

class SettingsTab extends JPanel {
    SettingsTab(SettingsComponent... settingsComponentList) {
        super(new GridBagLayout());

        for (int i = 0; i < settingsComponentList.length; i++)
        {
            SettingsComponent settingsComponent = settingsComponentList[i];
            boolean isLast = i + 1 == settingsComponentList.length;

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = i;
            constraints.anchor = FIRST_LINE_START;
            constraints.weightx = 1.0;
            constraints.weighty = isLast ? 1.0 : 0;
            constraints.fill = HORIZONTAL;

            this.add(new SettingsPanel(settingsComponent), constraints);
        }
    }
}
