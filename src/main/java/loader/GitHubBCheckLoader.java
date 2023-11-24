package loader;

import bcheck.BCheck;
import bcheck.BCheckFactory;
import client.GitHubClient;
import file.finder.BCheckFileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import settings.github.GitHubSettingsReader;

import java.nio.file.Path;
import java.util.List;

public class GitHubBCheckLoader implements BCheckLoader {
    private final GitHubClient gitHubClient;
    private final TempFileCreator tempFileCreator;
    private final ZipExtractor zipExtractor;
    private final BCheckFileFinder bCheckFileFinder;
    private final GitHubSettingsReader gitHubSettings;
    private final BCheckFactory bCheckFactory;

    public GitHubBCheckLoader(
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
        byte[] bChecksAsZip = gitHubClient.downloadRepoAsZip(gitHubSettings.repo(), gitHubSettings.apiKey());

        zipExtractor.extractZip(bChecksAsZip, bCheckDownloadLocation);

        return bCheckFileFinder.find(bCheckDownloadLocation)
                .stream()
                .map(bCheckFactory::fromFile)
                .toList();
    }
}