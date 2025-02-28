package data;

import java.nio.file.Path;

public interface ItemFactory<T extends Item> {
    T fromFile(Path filePath);
}