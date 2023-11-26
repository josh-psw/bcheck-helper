package repository;

import bcheck.BCheck;
import bcheck.BCheckFactory;
import file.finder.BCheckFileFinder;
import logging.Logger;
import settings.repository.filesystem.FileSystemRepositorySettingsReader;

import java.io.File;
import java.util.List;

public class FileSystemRepository implements Repository {
    private static final String EMPTY_LOCATION_MESSAGE = "Empty filesystem repository location";
    private static final String INVALID_LOCATION_MESSAGE = "Invalid filesystem repository location: ";

    private final BCheckFileFinder bCheckFileFinder;
    private final FileSystemRepositorySettingsReader settings;
    private final BCheckFactory bCheckFactory;
    private final Logger logger;

    public FileSystemRepository(FileSystemRepositorySettingsReader settings,
                                BCheckFileFinder bCheckFileFinder,
                                BCheckFactory bCheckFactory,
                                Logger logger) {
        this.bCheckFileFinder = bCheckFileFinder;
        this.settings = settings;
        this.bCheckFactory = bCheckFactory;
        this.logger = logger;
    }

    @Override
    public List<BCheck> loadAllBChecks() {
        if (settings.repositoryLocation().isEmpty()) {
            logger.logError(EMPTY_LOCATION_MESSAGE);
            throw new IllegalStateException(EMPTY_LOCATION_MESSAGE);
        }

        File location = new File(settings.repositoryLocation());

        if (!location.exists()) {
            String message = INVALID_LOCATION_MESSAGE + location;
            logger.logError(message);
            throw new IllegalStateException(message);
        }

        return bCheckFileFinder.find(location.toPath())
                .stream()
                .map(bCheckFactory::fromFile)
                .toList();
    }
}