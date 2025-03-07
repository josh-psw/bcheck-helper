package settings.repository.github;

import burp.api.montoya.persistence.Preferences;
import data.RepositoryMetadata;
import settings.AbstractSettings;

public class GitHubSettings extends AbstractSettings implements GitHubSettingsReader {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String API_KEY_KEY = "github_settings.api_key"; //TODO should this be shared across instances or unique?

    private final RepositoryMetadata repositoryMetadata;

    public GitHubSettings(Preferences preferences, RepositoryMetadata repositoryMetadata) {
        super(preferences);
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public String repositoryUrl() {
        return loadStringFromPreferences(repositoryMetadata.getRepositoryUrlKey(), GITHUB_API_URL);
    }

    public void setRepositoryUrl(String repositoryName) {
        saveStringToPreferences(repositoryMetadata.getRepositoryUrlKey(), repositoryName);
    }

    @Override
    public String repositoryName() {
        return loadStringFromPreferences(repositoryMetadata.getRepositoryNameKey(), repositoryMetadata.getDefaultRepositoryNameValue());
    }

    public void setRepositoryName(String repositoryName) {
        saveStringToPreferences(repositoryMetadata.getRepositoryNameKey(), repositoryName);
    }

    @Override
    public String apiKey() {
        return loadStringFromPreferences(API_KEY_KEY, null);
    }

    public void setApiKey(String apiKey) {
        saveStringToPreferences(API_KEY_KEY, apiKey);
    }
}
