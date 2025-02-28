package settings.repository;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import repository.RepositoryType;
import settings.AbstractSettings;

import static repository.RepositoryType.GITHUB;

public class RepositorySettings extends AbstractSettings implements RepositorySettingsReader {
    static final String REPOSITORY_TYPE_KEY = "repository.type";
    private final ItemMetadata itemMetadata;

    public RepositorySettings(Preferences preferences, ItemMetadata itemMetadata) {
        super(preferences);
        this.itemMetadata = itemMetadata;
    }

    @Override
    public RepositoryType repositoryType() {
        String persistedKey = loadStringFromPreferences(itemMetadata.repositoryTypeKey, null);
        return persistedKey == null ? GITHUB : RepositoryType.fromPersistedKey(persistedKey);
    }

    public void setRepositoryType(RepositoryType repositoryType) {
        saveStringToPreferences(itemMetadata.repositoryTypeKey, repositoryType.persistedKey);
    }
}
