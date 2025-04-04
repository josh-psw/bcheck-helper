package settings.defaultsavelocation;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultSaveLocationSettingsTest {
    private final Preferences preferences = mock(Preferences.class);

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenNullUseDefaultLocationSetting_whenGetDefaultSaveLocation_thenEmptyOptionalReturned(ItemMetadata itemMetadata) {
        DefaultSaveLocationSettings defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, itemMetadata);
        when(preferences.getBoolean(itemMetadata.getUseSettingKey())).thenReturn(null);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenFalseUseDefaultLocationSetting_whenGetDefaultSaveLocation_thenEmptyOptionalReturned(ItemMetadata itemMetadata) {
        DefaultSaveLocationSettings defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, itemMetadata);
        when(preferences.getBoolean(itemMetadata.getUseSettingKey())).thenReturn(false);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenTrueUseDefaultLocationSetting_andNullSavedPath_whenGetDefaultSaveLocation_thenEmptyOptionalReturned(ItemMetadata itemMetadata) {
        DefaultSaveLocationSettings defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, itemMetadata);
        when(preferences.getBoolean(itemMetadata.getUseSettingKey())).thenReturn(true);
        when(preferences.getString(itemMetadata.getSaveLocationKey())).thenReturn(null);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(ItemMetadata.class)
    void givenTrueUseDefaultLocationSetting_andNonNullSavedPath_whenGetDefaultSaveLocation_thenOptionalContainingValidPathReturned(ItemMetadata itemMetadata) {
        String path = "/home/carlos";
        DefaultSaveLocationSettings defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, itemMetadata);

        when(preferences.getBoolean(itemMetadata.getUseSettingKey())).thenReturn(true);
        when(preferences.getString(itemMetadata.getSaveLocationKey())).thenReturn(path);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).contains(Path.of(path));
    }
}