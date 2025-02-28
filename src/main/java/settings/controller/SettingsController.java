package settings.controller;

import burp.api.montoya.persistence.Preferences;
import settings.debug.DebugSettings;

import static data.ItemMetadata.BAMBDA;
import static data.ItemMetadata.BCHECK;

public class SettingsController {
    private final ItemSettingsController bCheckSettingsController;
    private final ItemSettingsController bambdaSettingsController;
    private final DebugSettings debugSettings;

    public SettingsController(Preferences preferences) {
        this.bCheckSettingsController = new ItemSettingsController(preferences, BCHECK);
        this.bambdaSettingsController = new ItemSettingsController(preferences, BAMBDA);
        this.debugSettings = new DebugSettings(preferences);
    }

    public ItemSettingsController bCheckSettingsController() {
        return bCheckSettingsController;
    }

    public ItemSettingsController bambdaSettingsController() {
        return bambdaSettingsController;
    }

    public DebugSettings debugSettings() {
        return debugSettings;
    }
}
