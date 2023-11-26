package settings.repository.github;

import burp.api.montoya.persistence.Preferences;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubSettingsTest {
    private final Preferences preferences = mock(Preferences.class);
    private final GitHubSettings gitHubSettings = new GitHubSettings(preferences);

    @Test
    void givenNoRepoSet_whenGetRepositoryName_thenDefaultRepoReturned() {
        when(preferences.getString("github_settings.repo")).thenReturn(null);

        assertThat(gitHubSettings.repositoryName()).isEqualTo("portswigger/bchecks");
    }

    @Test
    void givenRepoSet_whenGetRepositoryName_thenSetRepoReturned() {
        String repo = "repo";

        when(preferences.getString("github_settings.repo")).thenReturn(repo);

        assertThat(gitHubSettings.repositoryName()).isEqualTo(repo);
    }

    @Test
    void givenNoUrlSet_whenGetRepositoryUrl_thenDefaultRepoReturned() {
        when(preferences.getString("github_settings.url")).thenReturn(null);

        assertThat(gitHubSettings.repositoryUrl()).isEqualTo("https://api.github.com");
    }

    @Test
    void givenUrlSet_whenGetRepositoryUrl_thenSetUrlReturned() {
        String url = "https://hackxor.net";

        when(preferences.getString("github_settings.url")).thenReturn(url);

        assertThat(gitHubSettings.repositoryUrl()).isEqualTo(url);
    }
}