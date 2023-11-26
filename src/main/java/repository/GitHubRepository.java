package repository;

import bcheck.BCheck;
import bcheck.BCheckFactory;
import client.GitHubClient;
import file.finder.BCheckFileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import settings.repository.github.GitHubSettingsReader;

import java.nio.file.Path;
import java.util.List;

public class GitHubRepository implements Repository {
    private final GitHubClient gitHubClient;
    private final TempFileCreator tempFileCreator;
    private final ZipExtractor zipExtractor;
    private final BCheckFileFinder bCheckFileFinder;
    private final GitHubSettingsReader gitHubSettings;
    private final BCheckFactory bCheckFactory;

    public GitHubRepository(
            BCheckFactory bCheckFactory,
            GitHubClient gitHubClient,
            TempFileCreator tempFileCreator,
            ZipExtractor zipExtractor,
            BCheckFileFinder bCheckFileFinder,
            GitHubSettingsReader gitHubSettings
    ) {
        this.bCheckFactory = bCheckFactory;
        this.gitHubClient = gitHubClient;
        this.tempFileCreator = tempFileCreator;
        this.zipExtractor = zipExtractor;
        this.bCheckFileFinder = bCheckFileFinder;
        this.gitHubSettings = gitHubSettings;
    }

    @Override
    public List<BCheck> loadAllBChecks() {
        Path bCheckDownloadLocation = tempFileCreator.createTempDirectory("bcheck-store");
        byte[] bChecksAsZip = gitHubClient.downloadRepoAsZip(
                gitHubSettings.repositoryUrl(),
                gitHubSettings.repositoryName(),
                gitHubSettings.apiKey()
        );

        zipExtractor.extractZip(bChecksAsZip, bCheckDownloadLocation);

        return bCheckFileFinder.find(bCheckDownloadLocation)
                .stream()
                .map(bCheckFactory::fromFile)
                .toList();
    }
}