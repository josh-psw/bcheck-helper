package settings.repository.github;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import settings.AbstractSettings;

public class GitHubSettings extends AbstractSettings implements GitHubSettingsReader {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String API_KEY_KEY = "github_settings.api_key"; //TODO should this be shared across instances or unique?
    private final ItemMetadata itemMetadata;

    public GitHubSettings(Preferences preferences, ItemMetadata itemMetadata) {
        super(preferences);
        this.itemMetadata = itemMetadata;
    }

    @Override
    public String repositoryUrl() {
        return loadStringFromPreferences(itemMetadata.repositoryUrlKey, GITHUB_API_URL);
    }

    public void setRepositoryUrl(String repositoryName) {
        saveStringToPreferences(itemMetadata.repositoryUrlKey, repositoryName);
    }

    @Override
    public String repositoryName() {
        return loadStringFromPreferences(itemMetadata.repositoryNameKey, itemMetadata.defaultRepositoryNameValue);
    }

    public void setRepositoryName(String repositoryName) {
        saveStringToPreferences(itemMetadata.repositoryNameKey, repositoryName);
    }

    @Override
    public String apiKey() {
        return loadStringFromPreferences(API_KEY_KEY, null);
    }

    public void setApiKey(String apiKey) {
        saveStringToPreferences(API_KEY_KEY, apiKey);
    }
}
