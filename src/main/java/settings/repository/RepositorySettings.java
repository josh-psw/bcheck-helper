package settings.repository;

import burp.api.montoya.persistence.Preferences;
import repository.RepositoryType;
import settings.AbstractSettings;

import static repository.RepositoryType.GITHUB;

public class RepositorySettings extends AbstractSettings implements RepositorySettingsReader {
    static final String REPOSITORY_TYPE_KEY = "repository.type";

    public RepositorySettings(Preferences preferences) {
        super(preferences);
    }

    @Override
    public RepositoryType repositoryType() {
        String persistedKey = loadStringFromPreferences(REPOSITORY_TYPE_KEY, null);
        return persistedKey == null ? GITHUB : RepositoryType.fromPersistedKey(persistedKey);
    }

    public void setRepositoryType(RepositoryType repositoryType) {
        saveStringToPreferences(REPOSITORY_TYPE_KEY, repositoryType.persistedKey);
    }
}
