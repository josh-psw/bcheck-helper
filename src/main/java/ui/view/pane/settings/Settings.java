package ui.view.pane.settings;

import settings.controller.SettingsController;

import javax.swing.*;

import static data.ItemMetadata.BAMBDA;
import static data.ItemMetadata.BCHECK;

public class Settings extends JTabbedPane {
    public Settings(SettingsController settingsController) {
        this.add(
                BCHECK.getName(),
                new SettingsTab(
                        new DefaultSaveLocationSettingsComponent(
                                settingsController.bCheckSettingsController().defaultSaveLocationSettings(),
                                BCHECK
                        ),
                        new RepositorySettingsComponent(
                                settingsController.bCheckSettingsController().repositorySettings(),
                                settingsController.bCheckSettingsController().gitHubSettings(),
                                settingsController.bCheckSettingsController().fileSystemRepositorySettings(),
                                BCHECK
                        )
                )
        );

        this.add(
                BAMBDA.getName(),
                new SettingsTab(
                        new DefaultSaveLocationSettingsComponent(
                                settingsController.bambdaSettingsController().defaultSaveLocationSettings(),
                                BAMBDA
                        ),
                        new RepositorySettingsComponent(
                                settingsController.bambdaSettingsController().repositorySettings(),
                                settingsController.bambdaSettingsController().gitHubSettings(),
                                settingsController.bambdaSettingsController().fileSystemRepositorySettings(),
                                BAMBDA
                        )
                )
        );

        this.add(
                "Debug settings",
                new SettingsTab(
                        new DebugSettingsComponent(settingsController.debugSettings()
                        )
                )
        );
    }
}
