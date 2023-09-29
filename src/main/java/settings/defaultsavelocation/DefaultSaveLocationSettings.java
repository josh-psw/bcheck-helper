package settings.defaultsavelocation;

import burp.api.montoya.persistence.Persistence;

import java.nio.file.Path;
import java.util.Optional;

public class DefaultSaveLocationSettings implements DefaultSaveLocationSettingsReader {
    private static final String USE_SETTING_KEY = "default_save_location.use_setting";
    private static final String SAVE_LOCATION_KEY = "default_save_location.save_location";
    private static final boolean DEFAULT_USE_SETTING_VALUE = false;

    private final Persistence persistence;

    public DefaultSaveLocationSettings(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public Optional<Path> defaultSaveLocation() {
        if (!useSetting()) {
            return Optional.empty();
        }

        String potentialPath = persistence.preferences().getString(SAVE_LOCATION_KEY);

        return potentialPath == null ? Optional.empty() : Optional.of(Path.of(potentialPath));
    }

    public void setDefaultSaveLocation(Path defaultSaveLocation) {
        persistence.preferences().setString(SAVE_LOCATION_KEY, defaultSaveLocation.toAbsolutePath().toString());
    }

    public void setUseSetting(boolean useSetting) {
        persistence.preferences().setBoolean(USE_SETTING_KEY, useSetting);
    }

    private boolean useSetting() {
        Boolean potentialSetting = persistence.preferences().getBoolean(USE_SETTING_KEY);

        if (potentialSetting == null || !potentialSetting) {
            return DEFAULT_USE_SETTING_VALUE;
        }

        return true;
    }
}
