package settings.controller;

import burp.api.montoya.persistence.Preferences;
import settings.debug.DebugSettings;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.repository.RepositorySettings;
import settings.repository.filesystem.FileSystemRepositorySettings;
import settings.repository.github.GitHubSettings;

import static data.ItemMetadata.BAMBDA;
import static data.ItemMetadata.BCHECK;

public class SettingsController {
    private final DefaultSaveLocationSettings bCheckDefaultSaveLocationSettings;
    private final DefaultSaveLocationSettings bambdaDefaultSaveLocationSettings;
    private final GitHubSettings bCheckGitHubSettings;
    private final GitHubSettings bambdaGitHubSettings;
    private final FileSystemRepositorySettings bCheckFileSystemRepositorySettings;
    private final FileSystemRepositorySettings bambdaFileSystemRepositorySettings;
    private final RepositorySettings bCheckRepositorySettings;
    private final RepositorySettings bambdaRepositorySettings;
    private final DebugSettings debugSettings;

    public SettingsController(Preferences preferences) {
        this.bCheckDefaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, BCHECK);
        this.bCheckRepositorySettings = new RepositorySettings(preferences, BCHECK);
        this.bCheckGitHubSettings = new GitHubSettings(preferences, BCHECK);
        this.bCheckFileSystemRepositorySettings = new FileSystemRepositorySettings(preferences, BCHECK);

        this.bambdaDefaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, BAMBDA);
        this.bambdaRepositorySettings = new RepositorySettings(preferences, BAMBDA);
        this.bambdaGitHubSettings = new GitHubSettings(preferences, BAMBDA);
        this.bambdaFileSystemRepositorySettings = new FileSystemRepositorySettings(preferences, BAMBDA);

        this.debugSettings = new DebugSettings(preferences);
    }

    public DefaultSaveLocationSettings bCheckDefaultSaveLocationSettings() {
        return bCheckDefaultSaveLocationSettings;
    }

    public DefaultSaveLocationSettings bambdaDefaultSaveLocationSettings() {
        return bambdaDefaultSaveLocationSettings;
    }

    public RepositorySettings bCheckRepositorySettings() {
        return bCheckRepositorySettings;
    }

    public RepositorySettings bambdaRepositorySettings() {
        return bambdaRepositorySettings;
    }

    public GitHubSettings bCheckGitHubSettings() {
        return bCheckGitHubSettings;
    }

    public GitHubSettings bambdaGitHubSettings() {
        return bambdaGitHubSettings;
    }

    public FileSystemRepositorySettings bCheckFileSystemRepositorySettings() {
        return bCheckFileSystemRepositorySettings;
    }

    public FileSystemRepositorySettings bambdaFileSystemRepositorySettings() {
        return bambdaFileSystemRepositorySettings;
    }

    public DebugSettings debugSettings() {
        return debugSettings;
    }
}
