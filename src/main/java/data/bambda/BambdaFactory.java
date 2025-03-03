package data.bambda;

import data.ItemFactory;
import logging.Logger;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readString;

public class BambdaFactory implements ItemFactory<Bambda> {
    private final Logger logger;
    private final BambdaParser parser;

    public BambdaFactory(Logger logger) {
        this.logger = logger;
        this.parser = new BambdaParser();
    }

    @Override
    public Bambda fromFile(Path filePath) {
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
