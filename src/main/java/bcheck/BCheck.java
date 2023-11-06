package bcheck;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Files.readString;
import static java.util.Collections.unmodifiableList;

public class BCheck {
    private final String name;
    private final String description;
    private final String author;
    private final Tags tags;
    private final String path;
    private final String filename;

    private String script;

    BCheck(String name, String description, String author, List<String> tags, String path, String filename) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.path = path;
        this.filename = filename;

        this.tags = new Tags(tags);
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

    public Tags tags() {
        return tags;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCheck bCheck = (BCheck) o;
        return Objects.equals(name, bCheck.name) && Objects.equals(description, bCheck.description) &&
                Objects.equals(author, bCheck.author) && Objects.equals(tags, bCheck.tags) &&
                Objects.equals(path, bCheck.path) && Objects.equals(filename, bCheck.filename) &&
                Objects.equals(script, bCheck.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, author, tags, path, filename, script);
    }

    public static class Tags
    {
        private final List<String> tags;

        private Tags(List<String> tags) {
            this.tags = tags;
        }

        public List<String> tags() {
            return unmodifiableList(tags);
        }

        public boolean contains(String searchText) {
            return tags.stream().anyMatch(tag -> tag.equalsIgnoreCase(searchText));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tags tags1 = (Tags) o;
            return Objects.equals(tags, tags1.tags);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tags);
        }
    }
}
