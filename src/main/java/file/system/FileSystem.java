package file.system;

import logging.Logger;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.writeString;

public class FileSystem {
    private final Logger logger;

    public FileSystem(Logger logger) {
        this.logger = logger;
    }

    public void saveFile(String fileContents, Path path) {
        try {
            writeString(path, fileContents);
        } catch (IOException e) {
            logger.logError("Failed to save item: " + e);
            throw new IllegalStateException(e);
        }
    }
}
