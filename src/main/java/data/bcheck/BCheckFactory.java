package data.bcheck;

import data.ItemFactory;
import data.ItemParser;
import logging.Logger;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readString;

public class BCheckFactory implements ItemFactory<BCheck> {

    private final Logger logger;
    private final ItemParser<BCheck> parser;

    public BCheckFactory(Logger logger) {
        this.logger = logger;
        this.parser = new BCheckParser();
    }

    @Override
    public BCheck fromFile(Path filePath) {
        if (!filePath.toFile().isFile()) {
            throw new IllegalArgumentException(filePath + " is not a path");
        }

        try {
            String fileContents = readString(filePath);

            return parser.parse(filePath.getFileName().toString(), filePath.toFile().getAbsolutePath(), fileContents);
        } catch (IOException e) {
            logger.logError("Couldn't read file " + filePath + ": " + e);
            throw new IllegalStateException(e);
        }
    }
}