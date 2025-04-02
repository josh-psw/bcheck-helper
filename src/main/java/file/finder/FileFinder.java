package file.finder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.nio.file.Files.walk;

public class FileFinder {
    public List<Path> find(Path startingPath, String fileExtension) {
        try (Stream<Path> pathStream = walk(startingPath, FOLLOW_LINKS)) {
            List<String> filePaths = pathStream
                    .map(path -> path.toFile().getAbsolutePath())
                    .filter(path -> path.endsWith(fileExtension)).toList();

            return filePaths.stream()
                    .map(Path::of)
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
