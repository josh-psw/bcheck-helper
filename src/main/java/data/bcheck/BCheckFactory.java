package data.bcheck;

import data.ItemFactory;
import logging.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readString;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.regex.Pattern.compile;

public class BCheckFactory implements ItemFactory<BCheck> {
    private static final Pattern BCHECK_NAME_EXTRACTING_REGEX_PATTERN = compile("name:\\s\"(.+)\"");
    private static final Pattern BCHECK_AUTHOR_EXTRACTING_REGEX_PATTERN = compile("author:\\s\"(.+)\"");
    private static final Pattern BCHECK_DESCRIPTION_EXTRACTING_REGEX_PATTERN = compile("description:\\s\"(.+)\"");
    private static final Pattern BCHECK_TAG_EXTRACTING_REGEX_PATTERN = compile("tags:\\s(.+)");

    private final Logger logger;

    public BCheckFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public BCheck fromFile(Path filePath) {
        if (!filePath.toFile().isFile()) {
            throw new IllegalArgumentException(filePath + " is not a path");
        }

        try {
            String fileContents = readString(filePath);

            return parseFileContents(filePath, fileContents);
        } catch (IOException e) {
            logger.logError("Couldn't read file " + filePath + ": " + e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public BCheck parseFileContents(Path filePath, String fileContents) {
        Matcher bCheckNameMatcher = BCheckFactory.BCHECK_NAME_EXTRACTING_REGEX_PATTERN.matcher(fileContents);
        Matcher bCheckAuthorMatcher = BCheckFactory.BCHECK_AUTHOR_EXTRACTING_REGEX_PATTERN.matcher(fileContents);
        Matcher bCheckDescriptionMatcher = BCheckFactory.BCHECK_DESCRIPTION_EXTRACTING_REGEX_PATTERN.matcher(fileContents);
        Matcher bCheckTagMatcher = BCheckFactory.BCHECK_TAG_EXTRACTING_REGEX_PATTERN.matcher(fileContents);

        String name = bCheckNameMatcher.find() ?
                bCheckNameMatcher.group(1) :
                "No name";

        String author = bCheckAuthorMatcher.find() ?
                bCheckAuthorMatcher.group(1) :
                "No author";

        String description = bCheckDescriptionMatcher.find() ?
                bCheckDescriptionMatcher.group(1) :
                "No description";

        List<String> tags = bCheckTagMatcher.find() ?
                stream(bCheckTagMatcher.group(1).split(","))
                        .map(quotedTag -> quotedTag.replace("\"", ""))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .toList() :
                emptyList();

        String bCheckFilename = filePath.getFileName().toString();

        return new BCheck(name, description, author, tags, filePath.toFile().getAbsolutePath(), bCheckFilename);
    }
}
