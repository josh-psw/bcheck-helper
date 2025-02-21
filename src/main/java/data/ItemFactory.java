package data;

import java.nio.file.Path;

public interface ItemFactory {
    Item fromFile(Path filePath);

    Item parseFileContents(Path filePath, String fileContents);
}