package settings.debug;

import burp.api.montoya.persistence.Persistence;
import burp.api.montoya.persistence.Preferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static settings.debug.DebugSettings.LOGGING_KEY;

class DebugSettingsTest {
    private final Persistence persistence = mock(Persistence.class);
    private final Preferences preferences = mock(Preferences.class);
    private final DebugSettings debugSettings = new DebugSettings(persistence);

    @BeforeEach
    void initialiseMock() {
        when(persistence.preferences()).thenReturn(preferences);
    }

    @Test
    void givenLoggingFlagNotSet_whenLogging_thenFalseReturned() {
        assertThat(debugSettings.logging()).isFalse();
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void givenLoggingFlagSetTrue_whenLogging_thenFalseReturned(boolean flag) {
        when(persistence.preferences().getBoolean(LOGGING_KEY)).thenReturn(flag);

        assertThat(debugSettings.logging()).isEqualTo(flag);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void whenSetLogging_thenPreferencesUpdated(boolean flag) {
        debugSettings.setLogging(flag);

        verify(preferences).setBoolean(LOGGING_KEY, flag);
    }
}