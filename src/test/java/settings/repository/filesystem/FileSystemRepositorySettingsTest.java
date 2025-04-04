package settings.repository.filesystem;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FileSystemRepositorySettingsTest {
    private final Preferences preferences = mock(Preferences.class);

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenNoLocationSet_whenRepositoryLocation_thenEmptyStringReturned(ItemMetadata itemMetadata) {
        FileSystemRepositorySettings repositorySettings = new FileSystemRepositorySettings(preferences, itemMetadata);
        when(preferences.getString(itemMetadata.getFileSystemRepositoryLocationKey())).thenReturn(null);

        assertThat(repositorySettings.repositoryLocation()).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenLocationSet_whenRepositoryLocation_thenCorrectLocationReturned(ItemMetadata itemMetadata) {
        String location = "/home/peter.wiener/bchecks";
        FileSystemRepositorySettings repositorySettings = new FileSystemRepositorySettings(preferences, itemMetadata);

        when(preferences.getString(itemMetadata.getFileSystemRepositoryLocationKey())).thenReturn(location);

        assertThat(repositorySettings.repositoryLocation()).isEqualTo(location);
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void whenSetRepositoryLocation_thenLocationSavedToCorrectKey(ItemMetadata itemMetadata) {
        String location = "/home/peter.wiener/bchecks";
        FileSystemRepositorySettings repositorySettings = new FileSystemRepositorySettings(preferences, itemMetadata);

        repositorySettings.setRepositoryLocation(location);

        verify(preferences).setString(itemMetadata.getFileSystemRepositoryLocationKey(), location);
    }
}