package settings.controller;

import burp.api.montoya.persistence.Persistence;
import settings.debug.DebugSettings;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.github.GitHubSettings;

public class SettingsController {
    private final DefaultSaveLocationSettings defaultSaveLocationSettings;
    private final GitHubSettings gitHubSettings;
    private final DebugSettings debugSettings;

    public SettingsController(Persistence persistence) {
        this.defaultSaveLocationSettings = new DefaultSaveLocationSettings(persistence);
        this.gitHubSettings = new GitHubSettings(persistence);
        this.debugSettings = new DebugSettings(persistence);
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
