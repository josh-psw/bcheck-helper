package settings.controller;

import burp.api.montoya.persistence.Persistence;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.github.GitHubSettings;

public class SettingsController {
    private final DefaultSaveLocationSettings defaultSaveLocationSettings;
    private final GitHubSettings gitHubSettings;

    public SettingsController(Persistence persistence) {
        this.defaultSaveLocationSettings = new DefaultSaveLocationSettings(persistence);
        this.gitHubSettings = new GitHubSettings(persistence);
    }

    public DefaultSaveLocationSettings defaultSaveLocationSettings() {
        return defaultSaveLocationSettings;
    }

    public GitHubSettings gitHubSettings() {
        return gitHubSettings;
    }
}
