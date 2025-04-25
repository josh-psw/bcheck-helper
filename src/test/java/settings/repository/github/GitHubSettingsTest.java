package settings.repository.github;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubSettingsTest {
    private final Preferences preferences = mock(Preferences.class);

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenNoRepoSet_whenGetRepositoryName_thenDefaultRepoReturned(ItemMetadata itemMetadata) {
        GitHubSettings gitHubSettings = new GitHubSettings(preferences, itemMetadata);

        when(preferences.getString(itemMetadata.getRepositoryNameKey())).thenReturn(null);

        assertThat(gitHubSettings.repositoryName()).isEqualTo(itemMetadata.getDefaultRepositoryNameValue());
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenRepoSet_whenGetRepositoryName_thenSetRepoReturned(ItemMetadata itemMetadata) {
        String repo = "repo";
        GitHubSettings gitHubSettings = new GitHubSettings(preferences, itemMetadata);

        when(preferences.getString(itemMetadata.getRepositoryNameKey())).thenReturn(repo);

        assertThat(gitHubSettings.repositoryName()).isEqualTo(repo);
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenNoUrlSet_whenGetRepositoryUrl_thenDefaultRepoReturned(ItemMetadata itemMetadata) {
        GitHubSettings gitHubSettings = new GitHubSettings(preferences, itemMetadata);
        when(preferences.getString(itemMetadata.getRepositoryUrlKey())).thenReturn(null);

        assertThat(gitHubSettings.repositoryUrl()).isEqualTo("https://api.github.com");
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenUrlSet_whenGetRepositoryUrl_thenSetUrlReturned(ItemMetadata itemMetadata) {
        String url = "https://hackxor.net";
        GitHubSettings gitHubSettings = new GitHubSettings(preferences, itemMetadata);

        when(preferences.getString(itemMetadata.getRepositoryUrlKey())).thenReturn(url);

        assertThat(gitHubSettings.repositoryUrl()).isEqualTo(url);
    }
}