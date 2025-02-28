package data;

public enum ItemMetadata
{
    BCHECK(
            "BCheck",
            ".bcheck",
            "bcheck-store",
            "repository.type",
            "default_save_location.use_setting",
            "default_save_location.save_location" ),
    BAMBDA(
            "Bambda",
            ".bambda",
            "bambda-store",
            "bambda.repository.type",
            "bambda.default_save_location.use_setting",
            "bambda.default_save_location.save_location");


    public final String name;
    public final String fileExtension;
    public final String tempDirectoryPrefix;
    public final String repositoryTypeKey;
    public final String useSettingKey;
    public final String saveLocationKey;

    ItemMetadata(
            String name,
            String fileExtension,
            String tempDirectoryPrefix,
            String repositoryTypeKey,
            String useSettingKey,
            String saveLocationKey) {
        this.name = name;
        this.fileExtension = fileExtension;
        this.tempDirectoryPrefix = tempDirectoryPrefix;
        this.repositoryTypeKey = repositoryTypeKey;
        this.useSettingKey = useSettingKey;
        this.saveLocationKey = saveLocationKey;
    }
}
