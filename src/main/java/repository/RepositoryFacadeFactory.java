package repository;

import bcheck.BCheckFactory;
import burp.api.montoya.http.Http;
import client.GitHubClient;
import file.finder.BCheckFileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import logging.Logger;
import network.RequestSender;
import settings.controller.SettingsController;

public class RepositoryFacadeFactory {

    public static Repository from(Logger logger, Http http, SettingsController settingsController) {
        RequestSender requestSender = new RequestSender(http, logger);
        BCheckFactory bCheckFactory = new BCheckFactory(logger);
        GitHubClient gitHubClient = new GitHubClient(requestSender);
        TempFileCreator tempFileCreator = new TempFileCreator(logger);
        ZipExtractor zipExtractor = new ZipExtractor(logger);
        BCheckFileFinder bCheckFileFinder = new BCheckFileFinder();

        GitHubRepository gitHubRepository = new GitHubRepository(
                bCheckFactory,
                gitHubClient,
                tempFileCreator,
                zipExtractor,
                bCheckFileFinder,
                settingsController.gitHubSettings()
        );

        FileSystemRepository fileSystemRepository = new FileSystemRepository(
                settingsController.fileSystemRepositorySettings(),
                bCheckFileFinder,
                bCheckFactory,
                logger
        );

        return new RepositoryFacade(
                settingsController.repositorySettings(),
                gitHubRepository,
                fileSystemRepository
        );
    }
}
