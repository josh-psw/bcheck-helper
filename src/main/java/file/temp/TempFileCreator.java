package file.temp;

import logging.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileCreator {
    private final Logger logger;

    public TempFileCreator(Logger logger) {
        this.logger = logger;
    }

    public Path createTempDirectory(String prefix) {
        try {
            Path tempDirectory = Files.createTempDirectory(prefix);
            File tempDirectoryAsFile = tempDirectory.toFile();

            tempDirectoryAsFile.deleteOnExit();

            logger.logDebug("Created temp directory: " + tempDirectoryAsFile.getAbsolutePath());

            return tempDirectory;
        } catch (IOException e) {
            logger.logError("Failed to create temp directory. Exception: " + e);
            throw new IllegalStateException(e);
        }
    }
}
