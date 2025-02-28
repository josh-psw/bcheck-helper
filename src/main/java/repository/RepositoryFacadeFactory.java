package repository;

import burp.api.montoya.http.Http;
import client.GitHubClient;
import data.bcheck.BCheck;
import data.bcheck.BCheckFactory;
import file.finder.FileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import logging.Logger;
import network.RequestSender;
import settings.controller.ItemSettingsController;

import static data.ItemMetadata.BCHECK;

public class RepositoryFacadeFactory {

    public static Repository<BCheck> from(Logger logger, Http http, ItemSettingsController settingsController) {
        RequestSender requestSender = new RequestSender(http, logger);
        BCheckFactory bCheckFactory = new BCheckFactory(logger);
        GitHubClient gitHubClient = new GitHubClient(requestSender);
        TempFileCreator tempFileCreator = new TempFileCreator(logger);
        ZipExtractor zipExtractor = new ZipExtractor(logger);
        FileFinder bCheckFileFinder = new FileFinder();

        GitHubRepository<BCheck> gitHubRepository = new GitHubRepository<>(
                bCheckFactory,
                gitHubClient,
                tempFileCreator,
                zipExtractor,
                bCheckFileFinder,
                settingsController.gitHubSettings(),
                BCHECK
        );

        FileSystemRepository<BCheck> fileSystemRepository = new FileSystemRepository<>(
                settingsController.fileSystemRepositorySettings(),
                bCheckFileFinder,
                bCheckFactory,
                logger,
                BCHECK
        );

        return new RepositoryFacade<>(
                settingsController.repositorySettings(),
                gitHubRepository,
                fileSystemRepository
        );
    }
}
