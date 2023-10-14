package file.finder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.writeString;
import static org.assertj.core.api.Assertions.assertThat;

class BCheckFileFinderTest {
    private final BCheckFileFinder bCheckFileFinder = new BCheckFileFinder();

    @Test
    void givenDirectoryStructure_withFolders_andWithBCheckFiles_andWithNonBCheckFiles_whenFindBChecks_thenAllBChecksFound_andNoNonBChecksFound(@TempDir Path directory) throws IOException {
        Path nonEmptySubDirectory = directory.resolve("non-empty-dir");
        Path emptySubDirectory = directory.resolve("empty-dir");
        createDirectory(nonEmptySubDirectory);
        createDirectory(emptySubDirectory);

        writeString(directory.resolve("bcheck.bcheck"), "");
        writeString(directory.resolve("bcheck.bchec"), "");
        writeString(directory.resolve("bcheck"), "");

        writeString(nonEmptySubDirectory.resolve("sub-bcheck.bcheck"), "");
        writeString(nonEmptySubDirectory.resolve("sub-bcheck.bchec"), "");
        writeString(nonEmptySubDirectory.resolve("sub-bcheck"), "");

        List<Path> foundBChecks = bCheckFileFinder.find(directory);
        assertThat(foundBChecks).containsExactlyInAnyOrder(nonEmptySubDirectory.resolve("sub-bcheck.bcheck"), directory.resolve("bcheck.bcheck"));
    }
}