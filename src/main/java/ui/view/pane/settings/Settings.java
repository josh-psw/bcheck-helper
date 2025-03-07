package ui.view.pane.settings;

import settings.controller.SettingsController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static data.ItemMetadata.BAMBDA;
import static data.ItemMetadata.BCHECK;
import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;

public class Settings extends JPanel {

    public Settings(SettingsController settingsController) {
        setLayout(new GridBagLayout());

        List<SettingsComponent> settingsComponentList = List.of(
                new DefaultSaveLocationSettingsComponent(
                        settingsController.bCheckSettingsController().defaultSaveLocationSettings(),
                        BCHECK),
                new RepositorySettingsComponent(
                        settingsController.bCheckSettingsController().repositorySettings(),
                        settingsController.bCheckSettingsController().gitHubSettings(),
                        settingsController.bCheckSettingsController().fileSystemRepositorySettings(),
                        BCHECK),
                new DefaultSaveLocationSettingsComponent(
                        settingsController.bambdaSettingsController().defaultSaveLocationSettings(),
                        BAMBDA),
                new RepositorySettingsComponent(
                        settingsController.bambdaSettingsController().repositorySettings(),
                        settingsController.bambdaSettingsController().gitHubSettings(),
                        settingsController.bambdaSettingsController().fileSystemRepositorySettings(),
                        BAMBDA),
                new DebugSettingsComponent(settingsController.debugSettings())
        );

        for (int i = 0; i < settingsComponentList.size(); i++) {
            SettingsComponent settingsComponent = settingsComponentList.get(i);
            boolean isLast = i + 1 == settingsComponentList.size();

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = i;
            constraints.anchor = FIRST_LINE_START;
            constraints.weightx = 1.0;
            constraints.weighty = isLast ? 1.0 : 0;
            constraints.fill = HORIZONTAL;

            add(new SettingsPanel(settingsComponent), constraints);
        }
    }
}
