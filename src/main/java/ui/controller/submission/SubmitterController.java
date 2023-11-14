package ui.controller.submission;

import bcheck.BCheck;
import bcheck.BCheckFactory;
import burp.api.montoya.logging.Logging;
import client.github.GitHubClient;
import file.system.view.CustomFileSystemView;
import file.system.view.RepoFileSystemView;
import settings.github.GitHubSettingsReader;
import ui.controller.submission.result.Result;

import static java.util.UUID.randomUUID;
import static ui.controller.submission.result.Result.failed;
import static ui.controller.submission.result.Result.success;

public class SubmitterController {
    private static final String NON_ALPHANUMERIC_REGEX = "[^A-Za-z0-9]";
    private static final String PULL_REQUEST_TITLE_TEMPLATE = "Create BCheck: %s";
    private static final String PULL_REQUEST_CONTENT_TEMPLATE = """
            Name: %s
            Author: %s
            Description: %s
            """;

    private final GitHubSettingsReader gitHubSettingsReader;
    private final GitHubClient gitHubClient;
    private final BCheckFactory bCheckFactory;
    private final Logging logger;

    public SubmitterController(GitHubSettingsReader gitHubSettingsReader, GitHubClient gitHubClient, BCheckFactory bCheckFactory, Logging logger) {
        this.gitHubSettingsReader = gitHubSettingsReader;
        this.gitHubClient = gitHubClient;
        this.bCheckFactory = bCheckFactory;
        this.logger = logger;
    }

    //todo: make this return something more useful
    public Result<BCheck> submitBCheck(String bCheckContents) {
        var repo = gitHubSettingsReader.repo();
        var bCheck = bCheckFactory.fromString(bCheckContents);
        var branchName = randomUUID().toString()
                .toLowerCase()
                .replaceAll(NON_ALPHANUMERIC_REGEX, "");

        var prTitle = PULL_REQUEST_TITLE_TEMPLATE.formatted(bCheck.name());
        var prContent = PULL_REQUEST_CONTENT_TEMPLATE.formatted(bCheck.name(), bCheck.author(), bCheck.description());

        try {
            //todo: extract to bchecksubmitter somewhere else
            gitHubClient.createBranch(repo, branchName);
            gitHubClient.createFile(repo, bCheck.filename(), bCheckContents, branchName);
            gitHubClient.createPullRequest(repo, branchName, prTitle, prContent);
        } catch (Exception e) {
            logger.logToError(e);
            return failed(null); //todo: find a way to pass e in
        }

        return success(bCheck);
    }

    public Result<CustomFileSystemView> gitRepoAsFileSystem() {
        try {
            var repo = gitHubSettingsReader.repo();
            var folderPaths = gitHubClient.findDirectoriesInRepo(repo);

            var fileSystemView = new RepoFileSystemView(repo, folderPaths);
            return success(fileSystemView);
        } catch (Exception e) {
            return failed(null); //todo: find a way to pass e in
        }
    }
}
