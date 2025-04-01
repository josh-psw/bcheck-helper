package settings.repository;

import burp.api.montoya.persistence.Preferences;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import repository.RepositoryType;

import static data.ItemMetadata.BCHECK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static repository.RepositoryType.GITHUB;

class RepositorySettingsTest {
    private final Preferences preferences = mock(Preferences.class);
    private final RepositorySettings repositorySettings = new RepositorySettings(preferences, BCHECK);

    @Test
    void givenNoRepositoryType_whenRepositoryType_thenGitHubReturned() {
        when(preferences.getString(BCHECK.getRepositoryTypeKey())).thenReturn(null);

        assertThat(repositorySettings.repositoryType()).isEqualTo(GITHUB);
    }

    @EnumSource(RepositoryType.class)
    @ParameterizedTest
    void givenRepositoryTypeSet_whenRepositoryType_thenCorrectRepositoryTypeReturned(RepositoryType repositoryType) {
        when(preferences.getString(BCHECK.getRepositoryTypeKey())).thenReturn(repositoryType.persistedKey);

        assertThat(repositorySettings.repositoryType()).isEqualTo(repositoryType);
    }

    @EnumSource(RepositoryType.class)
    @ParameterizedTest
    void whenSetRepositoryType_thenPersistedKeySavedToCorrectKey(RepositoryType repositoryType) {
        repositorySettings.setRepositoryType(repositoryType);

        verify(preferences).setString(BCHECK.getRepositoryTypeKey(), repositoryType.persistedKey);
    }
}