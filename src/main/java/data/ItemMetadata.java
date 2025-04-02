package data;

public enum ItemMetadata implements RepositoryMetadata, SaveLocationMetadata, NameMetadata, ImportMetadata {
    BCHECK(
            "BCheck",
            ".bcheck",
            "bcheck",
            "portswigger/bchecks",
            true,
            "Import BChecks to your BCheck library."),
    BAMBDA(
            "Bambda",
            ".bambda",
            "bambda",
            "portswigger/bambdas",
            false,
            "Bambda import not yet supported.\nRegister your interest for this functionality at https://github.com/PortSwigger/burp-extensions-montoya-api/issues/112");

    private final String name;
    private final String fileExtension;
    private final String settingPrefix;
    private final String defaultRepositoryNameValue;
    private final boolean supportsImport;
    private final String importTooltipText;

    ItemMetadata(
            String name,
            String fileExtension,
            String settingPrefix,
            String defaultRepositoryNameValue,
            boolean supportsImport,
            String importTooltipText) {
        this.name = name;
        this.fileExtension = fileExtension;
        this.settingPrefix = settingPrefix;
        this.defaultRepositoryNameValue = defaultRepositoryNameValue;
        this.supportsImport = supportsImport;
        this.importTooltipText = importTooltipText;
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

    @Override
    public boolean isImportSupported() {
        return supportsImport;
    }

    @Override
    public String getImportTooltipText() {
        return importTooltipText;
    }
}
