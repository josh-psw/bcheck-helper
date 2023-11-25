package settings.github;

import burp.api.montoya.persistence.Persistence;
import burp.api.montoya.persistence.Preferences;

public class GitHubSettings implements GitHubSettingsReader {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String DEFAULT_REPOSITORY_NAME_VALUE = "portswigger/bchecks";
    private static final String REPOSITORY_NAME_KEY = "github_settings.repo";
    private static final String REPOSITORY_URL_KEY = "github_settings.url";
    private static final String API_KEY_KEY = "github_settings.api_key";

    private final Preferences preferences;

    public GitHubSettings(Persistence persistence) {
        this.preferences = persistence.preferences();
    }

    @Override
    public String repositoryUrl() {
        return loadStringFromPreferences(REPOSITORY_URL_KEY, GITHUB_API_URL);
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
        return preferences.getString(API_KEY_KEY);
    }

    public void setApiKey(String apiKey) {
        saveStringToPreferences(API_KEY_KEY, apiKey);
    }

    private String loadStringFromPreferences(String key, String defaultValue) {
        String value = preferences.getString(key);

        return value == null ? defaultValue : value;
    }

    private void saveStringToPreferences(String key, String value) {
        preferences.setString(key, value);
    }
}
