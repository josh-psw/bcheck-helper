package file.temp;

import burp.api.montoya.logging.Logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileCreator {
    private final Logging logger;

    public TempFileCreator(Logging logger) {
        this.logger = logger;
    }

    public Path createTempDirectory(String prefix) {
        try {
            Path tempDirectory = Files.createTempDirectory(prefix);
            File tempDirectoryAsFile = tempDirectory.toFile();

            tempDirectoryAsFile.deleteOnExit();

            logger.logToOutput("Created temp directory: " + tempDirectoryAsFile.getAbsolutePath());

            return tempDirectory;
        } catch (IOException e) {
            logger.logToError("Failed to create temp directory. Exception: " + e);
            throw new IllegalStateException(e);
        }
    }
}
