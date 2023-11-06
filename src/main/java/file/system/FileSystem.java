package file.system;

import burp.api.montoya.logging.Logging;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.writeString;

public class FileSystem {
    private final Logging logger;

    public FileSystem(Logging logger) {
        this.logger = logger;
    }

    public void saveFile(String fileContents, Path path) {
        try {
            writeString(path, fileContents);
        } catch (IOException e) {
            logger.logToError("Failed to save file: " + e);
            throw new IllegalStateException(e);
        }
    }
}
