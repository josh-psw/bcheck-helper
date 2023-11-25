package ui.view.pane.settings;

import settings.debug.DebugSettings;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;

class DebugSettingsComponent implements SettingsComponent {
    private final JComponent component;

    DebugSettingsComponent(DebugSettings debugSettings) {
        this.component = new JPanel(new BorderLayout());

        JCheckBox loggingCheckbox = new JCheckBox("Enabled");
        loggingCheckbox.setSelected(debugSettings.logging());
        loggingCheckbox.addActionListener(e -> debugSettings.setLogging(loggingCheckbox.isSelected()));

        component.add(loggingCheckbox, CENTER);
    }

    @Override
    public String title() {
        return "Logging";
    }

    @Override
    public String description() {
        return "Use this setting to enable or disable logging";
    }

    @Override
    public JComponent component() {
        return component;
    }
}
