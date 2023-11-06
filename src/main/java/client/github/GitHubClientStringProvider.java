package client.github;

import burp.api.montoya.logging.Logging;

import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

public class GitHubClientStringProvider {
    private static final Pattern DEFAULT_BRANCH_CAPTURING_REGEX_PATTERN = compile("\"default_branch\": \"(.*)\",");
    private static final String BRANCH_SHA_CAPTURING_REGEX_TEMPLATE = """
            \\"ref\\": \\"refs/heads/%s\\",
            \\s+\\"node_id\\": \\".*\\",
            \\s+\\"url\\": \\"https://api.github.com/repos/%s/git/refs/heads/%s\\",
            \\s+\\"object\\": \\{
            \\s+\\"sha\\": \\"(.*)\\"\
            """;
    private static final String REPO_ZIP_URL_TEMPLATE = "https://api.github.com/repos/%s/zipball";
    private static final String REPO_INFO_URL_TEMPLATE = "https://api.github.com/repos/%s";
    private static final String REPO_REF_HEAD_URL_TEMPLATE = "https://api.github.com/repos/%s/git/refs/heads";
    private static final String REPO_REFS_URL_TEMPLATE = "https://api.github.com/repos/%s/git/refs";
    private static final String REPO_CONTENT_URL_TEMPLATE = "https://api.github.com/repos/%s/contents/%s";
    private static final String REPO_PR_URL_TEMPLATE = "https://api.github.com/repos/%s/pulls";
    private static final String CREATE_BRANCH_BODY_TEMPLATE = """
            {
                "ref": "refs/heads/%s",
                "sha": "%s"
            }
            """;
    private static final String CREATE_FILE_BODY_TEMPLATE = """
            {
                "message": "Create BCheck file %s",
                "content": "%s",
                "branch": "%s"
            }
            """;
    private static final String CREATE_PR_BODY_TEMPLATE = """
            {
              "title": "%s",
              "body": "%s",
              "head": "%s",
              "base": "%s"
            }
            """;

    public static String repoZipUrl(String repo) {
        return format(REPO_ZIP_URL_TEMPLATE, repo);
    }

    public static String repoRefHeadUrl(String repo) {
        return format(REPO_REF_HEAD_URL_TEMPLATE, repo);
    }

    public static String repoRefUrl(String repo) {
        return format(REPO_REFS_URL_TEMPLATE, repo);
    }

    public static String repoInfoUrl(String repo) {
        return format(REPO_INFO_URL_TEMPLATE, repo);
    }

    public static String repoContentUrl(String repo, String fileName) {
        return REPO_CONTENT_URL_TEMPLATE.formatted(repo, fileName);
    }

    public static String repoPrUrl(String repo) {
        return REPO_PR_URL_TEMPLATE.formatted(repo);
    }

    public static String createFileBody(String fileName, String encodedContent, String branchName) {
        return CREATE_FILE_BODY_TEMPLATE.formatted(fileName, encodedContent, branchName);
    }

    public static String createBranchBody(String branchName, String defaultBranchSha) {
        return CREATE_BRANCH_BODY_TEMPLATE.formatted(branchName, defaultBranchSha);
    }

    public static String createPrBody(String title, String content, String branchName, String defaultBranch) {
        return CREATE_PR_BODY_TEMPLATE.formatted(title, content.replace("\n", "\\n"), branchName, defaultBranch);
    }

    public static Pattern branchShaCapturingPattern(String defaultBranch, String repo, Logging logger) {
        var regex = BRANCH_SHA_CAPTURING_REGEX_TEMPLATE.formatted(defaultBranch, repo, defaultBranch)
                .replace("/", "\\/");

        return compile(regex, CASE_INSENSITIVE);
    }

    public static Pattern defaultBranchCapturingPattern() {
        return DEFAULT_BRANCH_CAPTURING_REGEX_PATTERN;
    }
}
