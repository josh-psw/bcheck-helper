package settings.defaultsavelocation;

import burp.api.montoya.persistence.Preferences;
import data.SaveLocationMetadata;
import settings.AbstractSettings;

import java.nio.file.Path;
import java.util.Optional;

public class DefaultSaveLocationSettings extends AbstractSettings implements DefaultSaveLocationSettingsReader {
    private final SaveLocationMetadata saveLocationMetadata;

    public DefaultSaveLocationSettings(Preferences preferences, SaveLocationMetadata saveLocationMetadata) {
        super(preferences);
        this.saveLocationMetadata = saveLocationMetadata;
    }

    @Override
    public Optional<Path> defaultSaveLocation() {
        if (!useSetting()) {
            return Optional.empty();
        }

        String potentialPath = loadStringFromPreferences(saveLocationMetadata.getSaveLocationKey(), null);

        return potentialPath == null ? Optional.empty() : Optional.of(Path.of(potentialPath));
    }

    public void setDefaultSaveLocation(Path defaultSaveLocation) {
        saveStringToPreferences(saveLocationMetadata.getSaveLocationKey(), defaultSaveLocation.toAbsolutePath().toString());
    }

    public void setUseSetting(boolean useSetting) {
        saveBooleanToPreferences(saveLocationMetadata.getUseSettingKey(), useSetting);
    }

    private boolean useSetting() {
        return loadBooleanFromPreferences(saveLocationMetadata.getUseSettingKey(), false);
    }
}
