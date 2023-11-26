package settings.repository.github;

import burp.api.montoya.persistence.Preferences;
import settings.AbstractSettings;

public class GitHubSettings extends AbstractSettings implements GitHubSettingsReader {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String DEFAULT_REPOSITORY_NAME_VALUE = "portswigger/bchecks";
    private static final String REPOSITORY_NAME_KEY = "github_settings.repo";
    private static final String REPOSITORY_URL_KEY = "github_settings.url";
    private static final String API_KEY_KEY = "github_settings.api_key";

    public GitHubSettings(Preferences preferences) {
        super(preferences);
    }

    @Override
    public String repositoryUrl() {
        return loadStringFromPreferences(REPOSITORY_URL_KEY, GITHUB_API_URL);
    }

    public void setRepositoryUrl(String repositoryName) {
        saveStringToPreferences(REPOSITORY_URL_KEY, repositoryName);
    }

    @Override
    public String repositoryName() {
        return loadStringFromPreferences(REPOSITORY_NAME_KEY, DEFAULT_REPOSITORY_NAME_VALUE);
    }

    public void setRepositoryName(String repositoryName) {
        saveStringToPreferences(REPOSITORY_NAME_KEY, repositoryName);
    }

    @Override
    public String apiKey() {
        return loadStringFromPreferences(API_KEY_KEY, null);
    }

    public void setApiKey(String apiKey) {
        saveStringToPreferences(API_KEY_KEY, apiKey);
    }
}
