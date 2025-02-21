package file.zip;

import logging.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectory;

public class ZipExtractor {
    private final Logger logger;

    public ZipExtractor(Logger logger) {
        this.logger = logger;
    }

    public void extractZip(byte[] zip, Path extractLocation) {
        try (ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(zip))) {
            ZipEntry entry;

            while ((entry = zipStream.getNextEntry()) != null)
            {
                Path itemCopyPath = extractLocation.resolve(entry.getName());

                if (entry.isDirectory()) {
                    logger.logDebug("Creating directory: " + itemCopyPath);
                    createDirectory(itemCopyPath);
                } else {
                    logger.logDebug("Copying file: " + itemCopyPath);
                    copy(zipStream, itemCopyPath);
                }
            }
        } catch (IOException e) {
            logger.logError("Failed to deserialize ZIP response. Exception: " + e);
            throw new IllegalStateException(e);
        }

        logger.logInfo("Successfully extracted items.");
    }
}
