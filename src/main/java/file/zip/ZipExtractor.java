package file.zip;

import burp.api.montoya.logging.Logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectory;

public class ZipExtractor {
    private final Logging logger;

    public ZipExtractor(Logging logger) {
        this.logger = logger;
    }

    public void extractZip(byte[] zip, Path extractLocation) {
        try (ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(zip))) {
            ZipEntry entry;

            while ((entry = zipStream.getNextEntry()) != null)
            {
                Path bCheckCopyPath = extractLocation.resolve(entry.getName());

                if (entry.isDirectory()) {
                    logger.logToOutput("Creating directory: " + bCheckCopyPath);
                    createDirectory(bCheckCopyPath);
                } else {
                    logger.logToOutput("Copying file: " + bCheckCopyPath);
                    copy(zipStream, bCheckCopyPath);
                }
            }
        } catch (IOException e) {
            logger.logToError("Failed to deserialise ZIP response. Exception: " + e);
            throw new IllegalStateException(e);
        }

        logger.logToOutput("Successfully extracted BChecks");
    }
}
