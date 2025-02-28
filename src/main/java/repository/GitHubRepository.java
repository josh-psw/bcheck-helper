package repository;

import client.GitHubClient;
import data.ItemMetadata;
import data.bcheck.BCheck;
import data.bcheck.BCheckFactory;
import file.finder.FileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import settings.repository.github.GitHubSettingsReader;

import java.nio.file.Path;
import java.util.List;

public class GitHubRepository implements Repository<BCheck> {
    private final GitHubClient gitHubClient;
    private final TempFileCreator tempFileCreator;
    private final ZipExtractor zipExtractor;
    private final FileFinder bCheckFileFinder;
    private final GitHubSettingsReader gitHubSettings;
    private final ItemMetadata itemMetadata;
    private final BCheckFactory bCheckFactory;

    public GitHubRepository(
            BCheckFactory bCheckFactory,
            GitHubClient gitHubClient,
            TempFileCreator tempFileCreator,
            ZipExtractor zipExtractor,
            FileFinder bCheckFileFinder,
            GitHubSettingsReader gitHubSettings,
            ItemMetadata itemMetadata) {
        this.bCheckFactory = bCheckFactory;
        this.gitHubClient = gitHubClient;
        this.tempFileCreator = tempFileCreator;
        this.zipExtractor = zipExtractor;
        this.bCheckFileFinder = bCheckFileFinder;
        this.gitHubSettings = gitHubSettings;
        this.itemMetadata = itemMetadata;
    }

    @Override
    public List<BCheck> loadAllItems() {
        Path bCheckDownloadLocation = tempFileCreator.createTempDirectory(itemMetadata.tempDirectoryPrefix);
        byte[] bChecksAsZip = gitHubClient.downloadRepoAsZip(
                gitHubSettings.repositoryUrl(),
                gitHubSettings.repositoryName(),
                gitHubSettings.apiKey()
        );

        zipExtractor.extractZip(bChecksAsZip, bCheckDownloadLocation);

        return bCheckFileFinder.find(bCheckDownloadLocation, itemMetadata.fileExtension)
                .stream()
                .map(bCheckFactory::fromFile)
                .toList();
    }
}