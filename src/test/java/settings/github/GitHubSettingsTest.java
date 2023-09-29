package settings.github;

import burp.api.montoya.persistence.Persistence;
import burp.api.montoya.persistence.Preferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubSettingsTest {
    private final Persistence persistence = mock(Persistence.class);
    private final Preferences preferences = mock(Preferences.class);
    private final GitHubSettings gitHubSettings = new GitHubSettings(persistence);

    @BeforeEach
    void initialiseMock() {
        when(persistence.preferences()).thenReturn(preferences);
    }

    @Test
    void givenNoRepoSet_whenGetRepo_thenDefaultRepoReturned() {
        when(persistence.preferences().getString("github_settings.repo")).thenReturn(null);

        assertThat(gitHubSettings.repo()).isEqualTo("portswigger/bchecks");
    }

    @Test
    void givenRepoSet_whenGetRepo_thenSetRepoReturned() {
        String repo = "repo";

        when(persistence.preferences().getString("github_settings.repo")).thenReturn(repo);

        assertThat(gitHubSettings.repo()).isEqualTo(repo);
    }
}