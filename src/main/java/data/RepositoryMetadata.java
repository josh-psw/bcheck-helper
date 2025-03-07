package data;

public interface RepositoryMetadata {
    String getRepositoryTypeKey();

    String getRepositoryNameKey();

    String getRepositoryUrlKey();

    String getDefaultRepositoryNameValue();

    String getFileSystemRepositoryLocationKey();

    String getFileExtension();

    String getTempDirectoryPrefix();
}
