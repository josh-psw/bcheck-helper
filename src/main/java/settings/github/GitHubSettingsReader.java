package settings.github;

public interface GitHubSettingsReader {
    String repo();

    ApiKey apiKey();
}
