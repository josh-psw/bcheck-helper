package repository;

import burp.api.montoya.http.Http;
import client.GitHubClient;
import data.Item;
import data.ItemFactory;
import data.RepositoryMetadata;
import file.finder.FileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import logging.Logger;
import network.RequestSender;
import settings.controller.ItemSettingsController;

public class RepositoryFacadeFactory {
    private final Logger logger;
    private final Http http;

    public RepositoryFacadeFactory(Logger logger, Http http) {
        this.logger = logger;
        this.http = http;
    }

    public <T extends Item> Repository<T> build(ItemSettingsController settingsController,
                               ItemFactory<T> itemFactory,
                               RepositoryMetadata repositoryMetadata) {
        RequestSender requestSender = new RequestSender(http, logger);
        GitHubClient gitHubClient = new GitHubClient(requestSender);
        TempFileCreator tempFileCreator = new TempFileCreator(logger);
        ZipExtractor zipExtractor = new ZipExtractor(logger);
        FileFinder fileFinder = new FileFinder();

        GitHubRepository<T> gitHubRepository = new GitHubRepository<>(
                itemFactory,
                gitHubClient,
                tempFileCreator,
                zipExtractor,
                fileFinder,
                settingsController.gitHubSettings(),
                repositoryMetadata
        );

        FileSystemRepository<T> fileSystemRepository = new FileSystemRepository<>(
                settingsController.fileSystemRepositorySettings(),
                fileFinder,
                itemFactory,
                logger,
                repositoryMetadata
        );

        return new RepositoryFacade<>(
                settingsController.repositorySettings(),
                gitHubRepository,
                fileSystemRepository
        );
    }
}
