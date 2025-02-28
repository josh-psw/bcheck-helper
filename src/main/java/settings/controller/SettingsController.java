package settings.controller;

import burp.api.montoya.persistence.Preferences;
import settings.debug.DebugSettings;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.repository.RepositorySettings;
import settings.repository.filesystem.FileSystemRepositorySettings;
import settings.repository.github.GitHubSettings;

import static data.ItemMetadata.BCHECK;

public class SettingsController {
    private final DefaultSaveLocationSettings bCheckDefaultSaveLocationSettings;
    private final GitHubSettings bCheckGitHubSettings;
    private final DebugSettings debugSettings;
    private final FileSystemRepositorySettings bCheckFileSystemRepositorySettings;
    private final RepositorySettings bCheckRepositorySettings;

    public SettingsController(Preferences preferences) {
        this.bCheckDefaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, BCHECK);
        this.bCheckRepositorySettings = new RepositorySettings(preferences, BCHECK);
        this.bCheckGitHubSettings = new GitHubSettings(preferences, BCHECK);
        this.bCheckFileSystemRepositorySettings = new FileSystemRepositorySettings(preferences, BCHECK);

        this.debugSettings = new DebugSettings(preferences);
    }

    public DefaultSaveLocationSettings bCheckDefaultSaveLocationSettings() {
        return bCheckDefaultSaveLocationSettings;
    }

    public RepositorySettings bCheckRepositorySettings() {
        return bCheckRepositorySettings;
    }

    public GitHubSettings bCheckGitHubSettings() {
        return bCheckGitHubSettings;
    }

    public FileSystemRepositorySettings bCheckFileSystemRepositorySettings() {
        return bCheckFileSystemRepositorySettings;
    }

    public DebugSettings debugSettings() {
        return debugSettings;
    }
}
