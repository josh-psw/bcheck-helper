package settings.repository.filesystem;

import burp.api.montoya.persistence.Preferences;
import data.RepositoryMetadata;
import settings.AbstractSettings;

public class FileSystemRepositorySettings extends AbstractSettings implements FileSystemRepositorySettingsReader {
    private final RepositoryMetadata repositoryMetadata;

    public FileSystemRepositorySettings(Preferences preferences, RepositoryMetadata repositoryMetadata) {
        super(preferences);
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public String repositoryLocation() {
        return loadStringFromPreferences(repositoryMetadata.getFileSystemRepositoryLocationKey(), "");
    }

    public void setRepositoryLocation(String location) {
        saveStringToPreferences(repositoryMetadata.getFileSystemRepositoryLocationKey(), location);
    }
}
