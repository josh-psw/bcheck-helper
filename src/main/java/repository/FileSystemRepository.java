package repository;

import data.ItemMetadata;
import data.bcheck.BCheck;
import data.bcheck.BCheckFactory;
import file.finder.FileFinder;
import logging.Logger;
import settings.repository.filesystem.FileSystemRepositorySettingsReader;

import java.io.File;
import java.util.List;

public class FileSystemRepository implements Repository<BCheck> {
    private static final String EMPTY_LOCATION_MESSAGE = "Empty filesystem repository location";
    private static final String INVALID_LOCATION_MESSAGE = "Invalid filesystem repository location: ";

    private final FileFinder bCheckFileFinder;
    private final FileSystemRepositorySettingsReader settings;
    private final BCheckFactory bCheckFactory;
    private final Logger logger;
    private final ItemMetadata itemMetadata;

    public FileSystemRepository(FileSystemRepositorySettingsReader settings,
                                FileFinder bCheckFileFinder,
                                BCheckFactory bCheckFactory,
                                Logger logger,
                                ItemMetadata itemMetadata) {
        this.bCheckFileFinder = bCheckFileFinder;
        this.settings = settings;
        this.bCheckFactory = bCheckFactory;
        this.logger = logger;
        this.itemMetadata = itemMetadata;
    }

    @Override
    public List<BCheck> loadAllItems() {
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

        return bCheckFileFinder.find(location.toPath(), itemMetadata.fileExtension)
                .stream()
                .map(bCheckFactory::fromFile)
                .toList();
    }
}