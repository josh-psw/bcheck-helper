package settings.debug;

import burp.api.montoya.persistence.Preferences;
import settings.AbstractSettings;

public class DebugSettings extends AbstractSettings {
    static final String LOGGING_KEY = "logging";

    public DebugSettings(Preferences preferences) {
        super(preferences);
    }

    public boolean logging() {
        return loadBooleanFromPreferences(LOGGING_KEY, false);
    }

    public void setLogging(boolean logging) {
        saveBooleanToPreferences(LOGGING_KEY, logging);
    }
}
