package settings;

import burp.api.montoya.persistence.Preferences;

public abstract class AbstractSettings {
    private final Preferences preferences;

    protected AbstractSettings(Preferences persistence) {
        this.preferences = persistence;
    }

    protected String loadStringFromPreferences(String key, String defaultValue) {
        String value = preferences.getString(key);

        return value == null ? defaultValue : value;
    }

    protected void saveStringToPreferences(String key, String value) {
        preferences.setString(key, value);
    }

    protected boolean loadBooleanFromPreferences(String key, boolean defaultValue) {
        Boolean value = preferences.getBoolean(key);

        return value == null ? defaultValue : value;
    }

    protected void saveBooleanToPreferences(String key, boolean value) {
        preferences.setBoolean(key, value);
    }
}
