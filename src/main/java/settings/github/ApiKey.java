package settings.github;

public class ApiKey {
    private final String key;

    public ApiKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    public boolean isValid() {
        return key != null && !key.isBlank();
    }

    @Override
    public String toString() {
        return key();
    }
}
