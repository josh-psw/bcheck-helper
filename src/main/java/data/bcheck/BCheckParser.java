package data.bcheck;

import data.ItemParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.regex.Pattern.compile;

public class BCheckParser implements ItemParser<BCheck> {
    private static final Pattern BCHECK_NAME_EXTRACTING_REGEX_PATTERN = compile("name:\\s\"(.+)\"");
    private static final Pattern BCHECK_AUTHOR_EXTRACTING_REGEX_PATTERN = compile("author:\\s\"(.+)\"");
    private static final Pattern BCHECK_DESCRIPTION_EXTRACTING_REGEX_PATTERN = compile("description:\\s\"(.+)\"");
    private static final Pattern BCHECK_TAG_EXTRACTING_REGEX_PATTERN = compile("tags:\\s(.+)");

    @Override
    public BCheck parse(String filename, String filePath, String fileContent) {
        Matcher bCheckNameMatcher = BCHECK_NAME_EXTRACTING_REGEX_PATTERN.matcher(fileContent);
        Matcher bCheckAuthorMatcher = BCHECK_AUTHOR_EXTRACTING_REGEX_PATTERN.matcher(fileContent);
        Matcher bCheckDescriptionMatcher = BCHECK_DESCRIPTION_EXTRACTING_REGEX_PATTERN.matcher(fileContent);
        Matcher bCheckTagMatcher = BCHECK_TAG_EXTRACTING_REGEX_PATTERN.matcher(fileContent);

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

        return new BCheck(name, description, author, tags, filePath, filename);
    }
}
