package data;

public enum ItemMetadata
{
    BCHECK(".bcheck", "bcheck-store"),
    BAMBDA(".bambda", "bambda-store");


    public final String fileExtension;
    public final String tempDirectoryPrefix;

    ItemMetadata(String fileExtension, String tempDirectoryPrefix)
    {
        this.fileExtension = fileExtension;
        this.tempDirectoryPrefix = tempDirectoryPrefix;
    }
}
