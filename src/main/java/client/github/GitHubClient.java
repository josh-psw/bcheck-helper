package client.github;

import burp.api.montoya.logging.Logging;
import network.RequestSender;
import settings.github.GitHubSettingsReader;

import java.util.Map;

import static client.github.GitHubClientStringProvider.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static java.util.Collections.emptyMap;

public class GitHubClient {
    private final RequestSender requestSender;
    private final GitHubSettingsReader gitHubSettingsReader;
    private final Logging logger;

    //todo: tidy up
    public GitHubClient(RequestSender requestSender, GitHubSettingsReader gitHubSettingsReader, Logging logger) {
        this.requestSender = requestSender;
        this.gitHubSettingsReader = gitHubSettingsReader;
        this.logger = logger;
    }

    public byte[] downloadRepoAsZip(String repo) {
        var url = repoZipUrl(repo);

        return requestSender.makeGetRequest(url, createHeaders(gitHubSettingsReader.apiKey())).body().getBytes();
    }

    public void createBranch(String repo, String branchName) {
        var headers = createHeaders(gitHubSettingsReader.apiKey());

        var defaultBranch = findRepoDefaultBranch(repo, headers);

        var repoRefHeadUrl = repoRefHeadUrl(repo);
        var repoRefHead = requestSender.makeGetRequest(repoRefHeadUrl, headers).body().getBytes();

        var branchShaCapturingRegexPattern = branchShaCapturingPattern(defaultBranch, repo, logger);
        var branchShaMatcher = branchShaCapturingRegexPattern.matcher(new String(repoRefHead));
        branchShaMatcher.find(); //todo: need to do something if it doesnt' work

        var defaultBranchSha = branchShaMatcher.group(1);

        var repoRefUrl = repoRefUrl(repo);
        var body = createBranchBody(branchName, defaultBranchSha);

        requestSender.makePostRequest(repoRefUrl, headers, body);
        logger.logToOutput("Created branch " + branchName + " on repo " + repo);
    }

    public void createFile(String repo, String fileName, String content, String branchName) {
        var url = repoContentUrl(repo, fileName);
        var headers = createHeaders(gitHubSettingsReader.apiKey());

        String encodedContent = getEncoder().encodeToString(content.getBytes(UTF_8));
        var requestBody = createFileBody(fileName, encodedContent, branchName);

        requestSender.makePutRequest(url, headers, requestBody);
        logger.logToOutput("Created file " + fileName);

    }
    public void createPullRequest(String repo, String branchName, String prTitle, String prContent) {
        var url = repoPrUrl(repo);
        var headers = createHeaders(gitHubSettingsReader.apiKey());

        var defaultBranch = findRepoDefaultBranch(repo, headers);
        var body = createPrBody(prTitle, prContent, branchName, defaultBranch);

        logger.logToOutput(body);

        requestSender.makePostRequest(url, headers, body);
    }

    private String findRepoDefaultBranch(String repo, Map<String, String> headers) {
        var repoInfoUrl = repoInfoUrl(repo);
        var repoInfo = requestSender.makeGetRequest(repoInfoUrl, headers).body().getBytes();

        var defaultBranchMatcher = defaultBranchCapturingPattern().matcher(new String(repoInfo));
        defaultBranchMatcher.find(); //todo: need to do something if it doesnt' work
        return defaultBranchMatcher.group(1);
    }

    private static Map<String, String> createHeaders(String apiKey) {
        return apiKey != null && !apiKey.isBlank() ?
                Map.of("Authorization", "Bearer " + apiKey) :
                emptyMap();
    }
}