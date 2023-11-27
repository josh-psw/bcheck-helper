package repository;

import static java.util.Arrays.stream;

public enum RepositoryType {
    FILESYSTEM("Filesystem", "filesystem"),
    GITHUB("GitHub", "github");

    public final String displayName;
    public final String persistedKey;

    RepositoryType(String displayName, String persistedKey) {
        this.displayName = displayName;
        this.persistedKey = persistedKey;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static RepositoryType fromPersistedKey(String persistedKey) {
        return stream(RepositoryType.values())
                .filter(v -> v.persistedKey.equals(persistedKey))
                .findFirst()
                .orElseThrow();
    }
}
