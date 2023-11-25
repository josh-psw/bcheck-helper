package ui.view.pane.settings;

import javax.swing.*;

interface SettingsComponent {
    String title();

    String description();

    JComponent component();
}
