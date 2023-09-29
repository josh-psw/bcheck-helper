package settings.github;

import burp.api.montoya.persistence.Persistence;

public class GitHubSettings implements GitHubSettingsReader {
    private static final String REPO_KEY = "github_settings.repo";
    private static final String API_KEY_KEY = "github_settings.api_key";
    private static final String DEFAULT_REPO_VALUE = "portswigger/bchecks";

    private final Persistence persistence;

    public GitHubSettings(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public String repo() {
        String potentialRepo = persistence.preferences().getString(REPO_KEY);

        return potentialRepo == null ? DEFAULT_REPO_VALUE : potentialRepo;
    }

    public void setRepo(String repo) {
        persistence.preferences().setString(REPO_KEY, repo);
    }

    @Override
    public String apiKey() {
        return persistence.preferences().getString(API_KEY_KEY);
    }

    public void setApiKey(String apiKey) {
        persistence.preferences().setString(API_KEY_KEY, apiKey);
    }
}
