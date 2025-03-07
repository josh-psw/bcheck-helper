package data;

public enum ItemMetadata implements RepositoryMetadata
{
    BCHECK(
            "BCheck",
            ".bcheck",
            "bcheck-store",
            "repository.type",
            "github_settings.repo",
            "github_settings.url",
            "portswigger/bchecks",
            "filesystem_repository.location",
            "default_save_location.use_setting",
            "default_save_location.save_location" ),
    BAMBDA(
            "Bambda",
            ".bambda",
            "bambda-store",
            "bambda.repository.type",
            "bambda.github_settings.repo",
            "bambda.github_settings.url",
            "portswigger/bambdas",
            "bambda.filesystem_repository.location",
            "bambda.default_save_location.use_setting",
            "bambda.default_save_location.save_location");


    public final String name;
    public final String fileExtension;
    public final String useSettingKey;
    public final String saveLocationKey;
    public final String tempDirectoryPrefix;

    private final String repositoryTypeKey;
    private final String repositoryNameKey;
    private final String repositoryUrlKey;
    private final String defaultRepositoryNameValue;
    private final String fileSystemRepositoryLocationKey;

    ItemMetadata(
            String name,
            String fileExtension,
            String tempDirectoryPrefix,
            String repositoryTypeKey,
            String repositoryNameKey,
            String repositoryUrlKey,
            String defaultRepositoryNameValue,
            String fileSystemRepositoryLocationKey,
            String useSettingKey,
            String saveLocationKey) {
        this.name = name;
        this.fileExtension = fileExtension;
        this.tempDirectoryPrefix = tempDirectoryPrefix;
        this.repositoryTypeKey = repositoryTypeKey;
        this.repositoryNameKey = repositoryNameKey;
        this.repositoryUrlKey = repositoryUrlKey;
        this.defaultRepositoryNameValue = defaultRepositoryNameValue;
        this.fileSystemRepositoryLocationKey = fileSystemRepositoryLocationKey;
        this.useSettingKey = useSettingKey;
        this.saveLocationKey = saveLocationKey;
    }

    @Override
    public String getRepositoryTypeKey()
    {
        return repositoryTypeKey;
    }

    @Override
    public String getRepositoryNameKey()
    {
        return repositoryNameKey;
    }

    @Override
    public String getRepositoryUrlKey()
    {
        return repositoryUrlKey;
    }

    @Override
    public String getDefaultRepositoryNameValue()
    {
        return defaultRepositoryNameValue;
    }

    @Override
    public String getFileSystemRepositoryLocationKey()
    {
        return fileSystemRepositoryLocationKey;
    }
}
