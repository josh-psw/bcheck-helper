package settings.github;

public interface GitHubSettingsReader {
    String repositoryUrl();

    String repositoryName();

    String apiKey();
}
