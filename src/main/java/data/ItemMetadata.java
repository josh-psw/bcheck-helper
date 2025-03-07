package data;

public enum ItemMetadata implements RepositoryMetadata, SaveLocationMetadata, NameMetadata {
    BCHECK(
            "BCheck",
            ".bcheck",
            "bcheck",
            "portswigger/bchecks"),
    BAMBDA(
            "Bambda",
            ".bambda",
            "bambda",
            "portswigger/bambdas");


    private final String name;
    private final String fileExtension;
    private final String settingPrefix;
    private final String defaultRepositoryNameValue;

    ItemMetadata(
            String name,
            String fileExtension,
            String settingPrefix,
            String defaultRepositoryNameValue) {
        this.name = name;
        this.fileExtension = fileExtension;
        this.settingPrefix = settingPrefix;
        this.defaultRepositoryNameValue = defaultRepositoryNameValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFileExtension() {
        return fileExtension;
    }

    @Override
    public String getDefaultRepositoryNameValue() {
        return defaultRepositoryNameValue;
    }

    @Override
    public String getRepositoryTypeKey() {
        return settingPrefix + ".repository.type";
    }

    @Override
    public String getRepositoryNameKey() {
        return settingPrefix + ".github_settings.repo";
    }

    @Override
    public String getRepositoryUrlKey() {
        return settingPrefix + ".github_settings.url";
    }

    @Override
    public String getFileSystemRepositoryLocationKey() {
        return settingPrefix + ".filesystem_repository.location";
    }

    @Override
    public String getUseSettingKey() {
        return settingPrefix + ".default_save_location.use_setting";
    }

    @Override
    public String getSaveLocationKey() {
        return settingPrefix + ".default_save_location.save_location";
    }

    @Override
    public String getTempDirectoryPrefix() {
        return settingPrefix + "-store";
    }
}
