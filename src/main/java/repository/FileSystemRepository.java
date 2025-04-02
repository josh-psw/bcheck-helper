package repository;

import data.Item;
import data.ItemFactory;
import data.RepositoryMetadata;
import file.finder.FileFinder;
import logging.Logger;
import settings.repository.filesystem.FileSystemRepositorySettingsReader;

import java.io.File;
import java.util.List;

public class FileSystemRepository<T extends Item> implements Repository<T> {
    private static final String EMPTY_LOCATION_MESSAGE = "Empty filesystem repository location";
    private static final String INVALID_LOCATION_MESSAGE = "Invalid filesystem repository location: ";

    private final FileFinder fileFinder;
    private final FileSystemRepositorySettingsReader settings;
    private final ItemFactory<T> itemFactory;
    private final Logger logger;
    private final RepositoryMetadata repositoryMetadata;

    public FileSystemRepository(FileSystemRepositorySettingsReader settings,
                                FileFinder fileFinder,
                                ItemFactory<T> itemFactory,
                                Logger logger,
                                RepositoryMetadata repositoryMetadata) {
        this.fileFinder = fileFinder;
        this.settings = settings;
        this.itemFactory = itemFactory;
        this.logger = logger;
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public List<T> loadAllItems() {
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

        return fileFinder.find(location.toPath(), repositoryMetadata.getFileExtension())
                .stream()
                .map(itemFactory::fromFile)
                .toList();
    }
}