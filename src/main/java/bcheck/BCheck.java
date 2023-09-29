package bcheck;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readString;
import static java.util.Collections.unmodifiableList;

public class BCheck {
    private final String name;
    private final String description;
    private final String author;
    private final List<String> tags;
    private final String path;
    private final String filename;

    private String script;

    BCheck(String name, String description, String author, List<String> tags, String path, String filename) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.tags = tags;
        this.path = path;
        this.filename = filename;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String author() {
        return author;
    }

    public String path() {
        return path;
    }

    public String filename() {
        return filename;
    }

    public List<String> tags() {
        return unmodifiableList(tags);
    }

    public String script() {
        if (script == null) {
            try {
                script = readString(Path.of(path));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        return script;
    }
}
