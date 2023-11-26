package settings.controller;

import burp.api.montoya.persistence.Preferences;
import settings.debug.DebugSettings;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.github.GitHubSettings;

public class SettingsController {
    private final DefaultSaveLocationSettings defaultSaveLocationSettings;
    private final GitHubSettings gitHubSettings;
    private final DebugSettings debugSettings;

    public SettingsController(Preferences preferences) {
        this.defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences);
        this.gitHubSettings = new GitHubSettings(preferences);
        this.debugSettings = new DebugSettings(preferences);
    }

    public DefaultSaveLocationSettings defaultSaveLocationSettings() {
        return defaultSaveLocationSettings;
    }

    public GitHubSettings gitHubSettings() {
        return gitHubSettings;
    }

    public DebugSettings debugSettings() {
        return debugSettings;
    }
}
