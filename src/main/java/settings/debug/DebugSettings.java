package settings.debug;

import burp.api.montoya.persistence.Persistence;

public class DebugSettings {
    static final String LOGGING_KEY = "logging";

    private final Persistence persistence;

    public DebugSettings(Persistence persistence) {
        this.persistence = persistence;
    }

    public boolean logging() {
        Boolean logging = persistence.preferences().getBoolean(LOGGING_KEY);
        return logging != null && logging;
    }

    public void setLogging(boolean logging) {
        persistence.preferences().setBoolean(LOGGING_KEY, logging);
    }
}
