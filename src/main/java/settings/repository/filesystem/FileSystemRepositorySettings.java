package settings.repository.filesystem;

import burp.api.montoya.persistence.Preferences;
import settings.AbstractSettings;

public class FileSystemRepositorySettings extends AbstractSettings implements FileSystemRepositorySettingsReader {
    static final String REPOSITORY_LOCATION_KEY = "filesystem_repository.location";

    public FileSystemRepositorySettings(Preferences preferences) {
        super(preferences);
    }

    @Override
    public String repositoryLocation() {
        return loadStringFromPreferences(REPOSITORY_LOCATION_KEY, "");
    }

    public void setRepositoryLocation(String location) {
        saveStringToPreferences(REPOSITORY_LOCATION_KEY, location);
    }
}
