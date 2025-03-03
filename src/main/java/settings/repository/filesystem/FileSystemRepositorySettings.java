package settings.repository.filesystem;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import settings.AbstractSettings;

public class FileSystemRepositorySettings extends AbstractSettings implements FileSystemRepositorySettingsReader {
    static final String REPOSITORY_LOCATION_KEY = "filesystem_repository.location";
    private final ItemMetadata itemMetadata;

    public FileSystemRepositorySettings(Preferences preferences, ItemMetadata itemMetadata) {
        super(preferences);
        this.itemMetadata = itemMetadata;
    }

    @Override
    public String repositoryLocation() {
        return loadStringFromPreferences(itemMetadata.fileSystemRepositoryLocationKey, "");
    }

    public void setRepositoryLocation(String location) {
        saveStringToPreferences(itemMetadata.fileSystemRepositoryLocationKey, location);
    }
}
