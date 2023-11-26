package settings.defaultsavelocation;

import burp.api.montoya.persistence.Preferences;
import settings.AbstractSettings;

import java.nio.file.Path;
import java.util.Optional;

public class DefaultSaveLocationSettings extends AbstractSettings implements DefaultSaveLocationSettingsReader {
    private static final String USE_SETTING_KEY = "default_save_location.use_setting";
    private static final String SAVE_LOCATION_KEY = "default_save_location.save_location";

    public DefaultSaveLocationSettings(Preferences preferences) {
        super(preferences);
    }

    @Override
    public Optional<Path> defaultSaveLocation() {
        if (!useSetting()) {
            return Optional.empty();
        }

        String potentialPath = loadStringFromPreferences(SAVE_LOCATION_KEY, null);

        return potentialPath == null ? Optional.empty() : Optional.of(Path.of(potentialPath));
    }

    public void setDefaultSaveLocation(Path defaultSaveLocation) {
        saveStringToPreferences(SAVE_LOCATION_KEY, defaultSaveLocation.toAbsolutePath().toString());
    }

    public void setUseSetting(boolean useSetting) {
        saveBooleanToPreferences(USE_SETTING_KEY, useSetting);
    }

    private boolean useSetting() {
        return loadBooleanFromPreferences(USE_SETTING_KEY, false);
    }
}
