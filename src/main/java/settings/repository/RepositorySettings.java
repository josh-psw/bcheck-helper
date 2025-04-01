package settings.repository;

import burp.api.montoya.persistence.Preferences;
import data.RepositoryMetadata;
import repository.RepositoryType;
import settings.AbstractSettings;

import static repository.RepositoryType.GITHUB;

public class RepositorySettings extends AbstractSettings implements RepositorySettingsReader {
    private final RepositoryMetadata repositoryMetadata;

    public RepositorySettings(Preferences preferences, RepositoryMetadata repositoryMetadata) {
        super(preferences);
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public RepositoryType repositoryType() {
        String persistedKey = loadStringFromPreferences(repositoryMetadata.getRepositoryTypeKey(), null);
        return persistedKey == null ? GITHUB : RepositoryType.fromPersistedKey(persistedKey);
    }

    public void setRepositoryType(RepositoryType repositoryType) {
        saveStringToPreferences(repositoryMetadata.getRepositoryTypeKey(), repositoryType.persistedKey);
    }
}
