package ui.controller.submission;

import bcheck.BCheckFactory;
import burp.api.montoya.logging.Logging;
import client.github.GitHubClient;
import settings.github.GitHubSettingsReader;

import static java.util.UUID.randomUUID;
import static ui.controller.submission.SubmissionResult.REQUEST_FAILED;
import static ui.controller.submission.SubmissionResult.SUCCESS;

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

    //todo: needs to prompt user where to put the bcheck

    public SubmissionResult submitBCheck(String bCheckContents) {
        var repo = gitHubSettingsReader.repo();
        var bCheck = bCheckFactory.fromString(bCheckContents);
        var branchName = randomUUID().toString()
                .toLowerCase()
                .replaceAll(NON_ALPHANUMERIC_REGEX, "");

        var prTitle = PULL_REQUEST_TITLE_TEMPLATE.formatted(bCheck.name());
        var prContent = PULL_REQUEST_CONTENT_TEMPLATE.formatted(bCheck.name(), bCheck.author(), bCheck.description());

        try {
            //todo: this probably shouldn't be in here - have a GitClient which has pushFileAndRaisePr?
            gitHubClient.createBranch(repo, branchName);
            gitHubClient.createFile(repo, bCheck.filename(), bCheckContents, branchName);
            gitHubClient.createPullRequest(repo, branchName, prTitle, prContent);
        } catch (Exception e) {
            logger.logToError(e);
            return REQUEST_FAILED;
        }

        return SUCCESS;
    }
}
