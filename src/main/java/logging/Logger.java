package logging;

import burp.api.montoya.logging.Logging;
import settings.debug.DebugSettings;

public class Logger {
    private final Logging logging;
    private final DebugSettings debugSettings;

    public Logger(Logging logging, DebugSettings debugSettings) {
        this.logging = logging;
        this.debugSettings = debugSettings;
    }

    public void logError(String error) {
        logging.logToError(error);
    }

    public void logError(Throwable throwable) {
        logging.logToError(throwable);
    }

    public void logInfo(String info) {
        logging.logToOutput(info);
    }

    public void logDebug(String debug) {
        if (debugSettings.logging()) {
            logging.logToOutput(debug);
        }
    }

    public void logEventLogError(String error) {
        logging.logToError(error);
        logging.raiseErrorEvent(error);
    }
}
