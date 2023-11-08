package ui.view.pane.settings;

import settings.controller.SettingsController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.Color.LIGHT_GRAY;
import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;

public class Settings extends JPanel {
    private final JComponent defaultSaveLocationSettingsPanel;
    private final JComponent gitHubSettingsPanel;
    private final JComponent debugSettingsPanel;

    public Settings(SettingsController settingsController) {
        setLayout(new GridBagLayout());

        this.defaultSaveLocationSettingsPanel = new DefaultSaveLocationSettingsPanel(settingsController.defaultSaveLocationSettings());
        this.gitHubSettingsPanel = new GitHubSettingsPanel(settingsController.gitHubSettings());
        this.debugSettingsPanel = new DebugSettingsPanel(settingsController.debugSettings());

        initialiseUi();
    }

    private void initialiseUi() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = FIRST_LINE_START;
        constraints.weightx = 1.0;
        constraints.fill = HORIZONTAL;

        defaultSaveLocationSettingsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        add(defaultSaveLocationSettingsPanel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = FIRST_LINE_START;
        constraints.weightx = 1.0;
        constraints.fill = HORIZONTAL;

        add(createSeparator(), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = FIRST_LINE_START;
        constraints.weightx = 1.0;
        constraints.fill = HORIZONTAL;

        gitHubSettingsPanel.setBorder(new EmptyBorder(15, 15, 25, 15));

        add(gitHubSettingsPanel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = FIRST_LINE_START;
        constraints.weightx = 1.0;
        constraints.fill = HORIZONTAL;

        add(createSeparator(), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = FIRST_LINE_START;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = HORIZONTAL;

        debugSettingsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        add(debugSettingsPanel, constraints);
    }

    private static JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(LIGHT_GRAY);
        return separator;
    }
}
