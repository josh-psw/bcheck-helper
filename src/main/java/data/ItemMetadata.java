package data;

public enum ItemMetadata
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
    public final String tempDirectoryPrefix;
    public final String repositoryTypeKey;
    public final String repositoryNameKey;
    public final String repositoryUrlKey;
    public final String defaultRepositoryNameValue;
    public final String fileSystemRepositoryLocationKey;
    public final String useSettingKey;
    public final String saveLocationKey;

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
}
