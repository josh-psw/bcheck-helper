package data;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;

public class Tags {
    private final List<String> tags;

    public Tags(List<String> tags) {
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