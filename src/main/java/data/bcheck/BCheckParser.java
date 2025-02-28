package data.bcheck;

import data.ItemParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.regex.Pattern.compile;

public class BCheckParser implements ItemParser<BCheck> {
    private static final Pattern NAME_EXTRACTING_REGEX_PATTERN = compile("name:\\s\"(.+)\"");
    private static final Pattern AUTHOR_EXTRACTING_REGEX_PATTERN = compile("author:\\s\"(.+)\"");
    private static final Pattern DESCRIPTION_EXTRACTING_REGEX_PATTERN = compile("description:\\s\"(.+)\"");
    private static final Pattern TAG_EXTRACTING_REGEX_PATTERN = compile("tags:\\s(.+)");

    @Override
    public BCheck parse(String filename, String filePath, String fileContent) {
        Matcher nameMatcher = NAME_EXTRACTING_REGEX_PATTERN.matcher(fileContent);
        Matcher authorMatcher = AUTHOR_EXTRACTING_REGEX_PATTERN.matcher(fileContent);
        Matcher descriptionMatcher = DESCRIPTION_EXTRACTING_REGEX_PATTERN.matcher(fileContent);
        Matcher tagMatcher = TAG_EXTRACTING_REGEX_PATTERN.matcher(fileContent);

        String name = nameMatcher.find() ?
                nameMatcher.group(1) :
                "No name";

        String author = authorMatcher.find() ?
                authorMatcher.group(1) :
                "No author";

        String description = descriptionMatcher.find() ?
                descriptionMatcher.group(1) :
                "No description";

        List<String> tags = tagMatcher.find() ?
                stream(tagMatcher.group(1).split(","))
                        .map(quotedTag -> quotedTag.replace("\"", ""))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .toList() :
                emptyList();

        return new BCheck(name, description, author, tags, filePath, filename);
    }
}
