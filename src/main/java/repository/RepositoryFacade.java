package repository;

import bcheck.BCheck;
import settings.repository.RepositorySettingsReader;

import java.util.List;

public class RepositoryFacade implements Repository {
    private final FileSystemRepository fileSystemRepository;
    private final GitHubRepository gitHubBCheckRepository;
    private final RepositorySettingsReader repositorySettingsReader;

    public RepositoryFacade(
            RepositorySettingsReader repositorySettingsReader,
            GitHubRepository gitHubBCheckRepository,
            FileSystemRepository fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
        this.gitHubBCheckRepository = gitHubBCheckRepository;
        this.repositorySettingsReader = repositorySettingsReader;
    }

    @Override
    public List<BCheck> loadAllBChecks() {
        Repository repository =  switch (repositorySettingsReader.repositoryType()) {
            case FILESYSTEM -> fileSystemRepository;
            case GITHUB -> gitHubBCheckRepository;
        };

        return repository.loadAllBChecks();
    }
}
