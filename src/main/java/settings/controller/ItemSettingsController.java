package settings.controller;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.repository.RepositorySettings;
import settings.repository.filesystem.FileSystemRepositorySettings;
import settings.repository.github.GitHubSettings;

public class ItemSettingsController {
    private final DefaultSaveLocationSettings defaultSaveLocationSettings;
    private final RepositorySettings repositorySettings;
    private final GitHubSettings gitHubSettings;
    private final FileSystemRepositorySettings fileSystemRepositorySettings;

    public ItemSettingsController(Preferences preferences, ItemMetadata itemMetadata) {
        this.defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, itemMetadata);
        this.repositorySettings = new RepositorySettings(preferences, itemMetadata);
        this.gitHubSettings = new GitHubSettings(preferences, itemMetadata);
        this.fileSystemRepositorySettings = new FileSystemRepositorySettings(preferences, itemMetadata);
    }

    public DefaultSaveLocationSettings defaultSaveLocationSettings() {
        return defaultSaveLocationSettings;
    }

    public RepositorySettings repositorySettings() {
        return repositorySettings;
    }

    public GitHubSettings gitHubSettings() {
        return gitHubSettings;
    }

    public FileSystemRepositorySettings fileSystemRepositorySettings() {
        return fileSystemRepositorySettings;
    }
}
