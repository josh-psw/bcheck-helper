package repository;

import client.GitHubClient;
import data.Item;
import data.ItemFactory;
import data.RepositoryMetadata;
import file.finder.FileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import settings.repository.github.GitHubSettingsReader;

import java.nio.file.Path;
import java.util.List;

public class GitHubRepository<T extends Item> implements Repository<T> {
    private final GitHubClient gitHubClient;
    private final TempFileCreator tempFileCreator;
    private final ZipExtractor zipExtractor;
    private final FileFinder fileFinder;
    private final GitHubSettingsReader gitHubSettings;
    private final RepositoryMetadata repositoryMetadata;
    private final ItemFactory<T> itemFactory;

    public GitHubRepository(
            ItemFactory<T> itemFactory,
            GitHubClient gitHubClient,
            TempFileCreator tempFileCreator,
            ZipExtractor zipExtractor,
            FileFinder fileFinder,
            GitHubSettingsReader gitHubSettings,
            RepositoryMetadata repositoryMetadata) {
        this.itemFactory = itemFactory;
        this.gitHubClient = gitHubClient;
        this.tempFileCreator = tempFileCreator;
        this.zipExtractor = zipExtractor;
        this.fileFinder = fileFinder;
        this.gitHubSettings = gitHubSettings;
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public List<T> loadAllItems() {
        Path downloadLocation = tempFileCreator.createTempDirectory(repositoryMetadata.getTempDirectoryPrefix());
        byte[] itemsAsZip = gitHubClient.downloadRepoAsZip(
                gitHubSettings.repositoryUrl(),
                gitHubSettings.repositoryName(),
                gitHubSettings.apiKey()
        );

        zipExtractor.extractZip(itemsAsZip, downloadLocation);

        return fileFinder.find(downloadLocation, repositoryMetadata.getFileExtension())
                .stream()
                .map(itemFactory::fromFile)
                .toList();
    }
}