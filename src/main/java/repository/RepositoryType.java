package repository;

import static java.util.Arrays.stream;

public enum RepositoryType {
    FILESYSTEM("filesystem"),
    GITHUB("github");

    public final String persistedKey;

    RepositoryType(String persistedKey) {
        this.persistedKey = persistedKey;
    }

    public static RepositoryType fromPersistedKey(String persistedKey) {
        return stream(RepositoryType.values())
                .filter(v -> v.persistedKey.equals(persistedKey))
                .findFirst()
                .orElseThrow();
    }
}
