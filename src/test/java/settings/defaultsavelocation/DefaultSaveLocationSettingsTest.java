package settings.defaultsavelocation;

import burp.api.montoya.persistence.Persistence;
import burp.api.montoya.persistence.Preferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultSaveLocationSettingsTest {
    private final Persistence persistence = mock(Persistence.class);
    private final Preferences preferences = mock(Preferences.class);
    private final DefaultSaveLocationSettings defaultSaveLocationSettings = new DefaultSaveLocationSettings(persistence);

    @BeforeEach
    void initialiseMock() {
        when(persistence.preferences()).thenReturn(preferences);
    }

    @Test
    void givenNullUseDefaultLocationSetting_whenGetDefaultSaveLocation_thenEmptyOptionalReturned() {
        when(persistence.preferences().getBoolean("default_save_location.use_setting")).thenReturn(null);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).isEmpty();
    }

    @Test
    void givenFalseUseDefaultLocationSetting_whenGetDefaultSaveLocation_thenEmptyOptionalReturned() {
        when(persistence.preferences().getBoolean("default_save_location.use_setting")).thenReturn(false);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).isEmpty();
    }

    @Test
    void givenTrueUseDefaultLocationSetting_andNullSavedPath_whenGetDefaultSaveLocation_thenEmptyOptionalReturned() {
        when(persistence.preferences().getBoolean("default_save_location.use_setting")).thenReturn(true);
        when(persistence.preferences().getString("default_save_location.save_location")).thenReturn(null);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).isEmpty();
    }

    @Test
    void givenTrueUseDefaultLocationSetting_andNonNullSavedPath_whenGetDefaultSaveLocation_thenOptionalContainingValidPathReturned() {
        String path = "/home/carlos";

        when(persistence.preferences().getBoolean("default_save_location.use_setting")).thenReturn(true);
        when(persistence.preferences().getString("default_save_location.save_location")).thenReturn(path);

        assertThat(defaultSaveLocationSettings.defaultSaveLocation()).contains(Path.of(path));
    }
}