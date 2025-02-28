package data;

public enum ItemMetadata
{
    BCHECK("BCheck", ".bcheck", "bcheck-store"),
    BAMBDA("Bambda", ".bambda", "bambda-store");


    public final String name;
    public final String fileExtension;
    public final String tempDirectoryPrefix;

    ItemMetadata(String name, String fileExtension, String tempDirectoryPrefix)
    {
        this.name = name;
        this.fileExtension = fileExtension;
        this.tempDirectoryPrefix = tempDirectoryPrefix;
    }
}
