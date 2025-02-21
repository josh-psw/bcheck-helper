package data.bcheck;

import data.Item;
import data.Tags;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Files.readString;

public class BCheck implements Item {
    private final String name;
    private final String description;
    private final String author;
    private final Tags tags;
    private final String path;
    private final String filename;

    private String content;

    BCheck(String name, String description, String author, List<String> tags, String path, String filename) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.path = path;
        this.filename = filename;

        this.tags = new Tags(tags);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    public String author() {
        return author;
    }

    public String path() {
        return path;
    }

    @Override
    public String filename() {
        return filename;
    }

    @Override
    public Tags tags() {
        return tags;
    }

    @Override
    public String content() {
        if (content == null) {
            try {
                content = readString(Path.of(path));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCheck bCheck = (BCheck) o;
        return Objects.equals(name, bCheck.name) && Objects.equals(description, bCheck.description) &&
                Objects.equals(author, bCheck.author) && Objects.equals(tags, bCheck.tags) &&
                Objects.equals(path, bCheck.path) && Objects.equals(filename, bCheck.filename) &&
                Objects.equals(content, bCheck.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, author, tags, path, filename, content);
    }
}
