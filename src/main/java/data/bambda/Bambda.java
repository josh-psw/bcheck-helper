package data.bambda;

import data.Item;
import data.Tags;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readString;

public class Bambda implements Item {
    private final String name;
    private final String description;
    private final Tags tags;
    private final String filename;
    private final String author;
    private final String path;

    private String content;

    public Bambda(String name, String description, String author, List<String> tags, String path, String filename)
    {
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
    public String filename() {
        return filename;
    }

    public String author() {
        return author;
    }
}
