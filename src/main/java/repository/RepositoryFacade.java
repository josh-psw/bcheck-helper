package repository;

import data.Item;
import settings.repository.RepositorySettingsReader;

import java.util.List;

public class RepositoryFacade<T extends Item> implements Repository<T> {
    private final FileSystemRepository<T> fileSystemRepository;
    private final GitHubRepository<T> gitHubRepository;
    private final RepositorySettingsReader repositorySettingsReader;

    public RepositoryFacade(
            RepositorySettingsReader repositorySettingsReader,
            GitHubRepository<T> gitHubRepository,
            FileSystemRepository<T> fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
        this.gitHubRepository = gitHubRepository;
        this.repositorySettingsReader = repositorySettingsReader;
    }

    @Override
    public List<T> loadAllItems() {
        Repository<T> repository =  switch (repositorySettingsReader.repositoryType()) {
            case FILESYSTEM -> fileSystemRepository;
            case GITHUB -> gitHubRepository;
        };

        return repository.loadAllItems();
    }
}
