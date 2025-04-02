package data.bambda;

import data.ItemParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Locale.US;
import static java.util.regex.Pattern.compile;

public class BambdaParser implements ItemParser<Bambda> {
    private static final Pattern NAME_EXTRACTING_REGEX_PATTERN = compile("name: (.+)");
    private static final Pattern AUTHOR_EXTRACTING_REGEX_PATTERN = compile("@author\\s+(.+)");
    private static final Pattern DESCRIPTION_EXTRACTING_REGEX_PATTERN = compile("\\*\\s*([^*\\n]+)\\s*\\n\\s*(?:\\*\\s*)*\\s*@author");
    private static final Pattern TAG_EXTRACTING_REGEX_PATTERN = compile("(?:function:|location:) (.+)");

    @Override
    public Bambda parse(String filename, String filePath, String fileContent) {
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

        List<String> tags = new ArrayList<>();
        while (tagMatcher.find()) {
            String tag  = tagMatcher.group(1);
            tags.add(tag.toLowerCase(US).replace('_', ' '));
        }

        return new Bambda(name, description, author, tags, filePath, filename);
    }
}
