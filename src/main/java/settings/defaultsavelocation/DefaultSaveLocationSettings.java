package settings.defaultsavelocation;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import settings.AbstractSettings;

import java.nio.file.Path;
import java.util.Optional;

public class DefaultSaveLocationSettings extends AbstractSettings implements DefaultSaveLocationSettingsReader {
    private final ItemMetadata itemMetadata;

    public DefaultSaveLocationSettings(Preferences preferences, ItemMetadata itemMetadata) {
        super(preferences);
        this.itemMetadata = itemMetadata;
    }

    @Override
    public Optional<Path> defaultSaveLocation() {
        if (!useSetting()) {
            return Optional.empty();
        }

        String potentialPath = loadStringFromPreferences(itemMetadata.saveLocationKey, null);

        return potentialPath == null ? Optional.empty() : Optional.of(Path.of(potentialPath));
    }

    public void setDefaultSaveLocation(Path defaultSaveLocation) {
        saveStringToPreferences(itemMetadata.saveLocationKey, defaultSaveLocation.toAbsolutePath().toString());
    }

    public void setUseSetting(boolean useSetting) {
        saveBooleanToPreferences(itemMetadata.useSettingKey, useSetting);
    }

    private boolean useSetting() {
        return loadBooleanFromPreferences(itemMetadata.useSettingKey, false);
    }
}
