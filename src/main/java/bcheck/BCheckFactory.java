package bcheck;

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

public class BCheckFactory {
    private static final Pattern BCHECK_NAME_EXTRACTING_REGEX_PATTERN = compile("name:\\s\"(.+)\"");
    private static final Pattern BCHECK_AUTHOR_EXTRACTING_REGEX_PATTERN = compile("author:\\s\"(.+)\"");
    private static final Pattern BCHECK_DESCRIPTION_EXTRACTING_REGEX_PATTERN = compile("description:\\s\"(.+)\"");
    private static final Pattern BCHECK_TAG_EXTRACTING_REGEX_PATTERN = compile("tags:\\s(.+)");

    private final Logger logger;

    public BCheckFactory(Logger logger) {
        this.logger = logger;
    }

    public BCheck fromFile(Path bCheckFilePath) {
        if (!bCheckFilePath.toFile().isFile()) {
            throw new IllegalArgumentException(bCheckFilePath + " is not a path");
        }

        try {
            String fileContents = readString(bCheckFilePath);

            return parseFileContents(bCheckFilePath, fileContents);
        } catch (IOException e) {
            logger.logError("Couldn't read BCheck file " + bCheckFilePath + ": " + e);
            throw new IllegalStateException(e);
        }
    }

    private BCheck parseFileContents(Path bCheckFilePath, String fileContents) {
        Matcher bCheckNameMatcher = BCHECK_NAME_EXTRACTING_REGEX_PATTERN.matcher(fileContents);
        Matcher bCheckAuthorMatcher = BCHECK_AUTHOR_EXTRACTING_REGEX_PATTERN.matcher(fileContents);
        Matcher bCheckDescriptionMatcher = BCHECK_DESCRIPTION_EXTRACTING_REGEX_PATTERN.matcher(fileContents);
        Matcher bCheckTagMatcher = BCHECK_TAG_EXTRACTING_REGEX_PATTERN.matcher(fileContents);

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

        String bCheckFilename = bCheckFilePath.getFileName().toString();

        return new BCheck(name, description, author, tags, bCheckFilePath.toFile().getAbsolutePath(), bCheckFilename);
    }
}
