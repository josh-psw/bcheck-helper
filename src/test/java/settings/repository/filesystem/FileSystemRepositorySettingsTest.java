package settings.repository.filesystem;

import burp.api.montoya.persistence.Preferences;
import org.junit.jupiter.api.Test;

import static data.ItemMetadata.BCHECK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FileSystemRepositorySettingsTest {
    private final Preferences preferences = mock(Preferences.class);
    private final FileSystemRepositorySettings repositorySettings = new FileSystemRepositorySettings(preferences, BCHECK);

    @Test
    void givenNoLocationSet_whenRepositoryLocation_thenEmptyStringReturned() {
        when(preferences.getString(BCHECK.getFileSystemRepositoryLocationKey())).thenReturn(null);

        assertThat(repositorySettings.repositoryLocation()).isEmpty();
    }

    @Test
    void givenLocationSet_whenRepositoryLocation_thenCorrectLocationReturned() {
        String location = "/home/peter.wiener/bchecks";

        when(preferences.getString(BCHECK.getFileSystemRepositoryLocationKey())).thenReturn(location);

        assertThat(repositorySettings.repositoryLocation()).isEqualTo(location);
    }

    @Test
    void whenSetRepositoryLocation_thenLocationSavedToCorrectKey() {
        String location = "/home/peter.wiener/bchecks";

        repositorySettings.setRepositoryLocation(location);

        verify(preferences).setString(BCHECK.getFileSystemRepositoryLocationKey(), location);
    }
}