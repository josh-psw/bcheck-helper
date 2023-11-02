package client;

import network.RequestSender;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.emptyMap;

public class GitHubClient {
    private static final String BCHECK_REPO_ZIP_URL_TEMPLATE = "https://api.github.com/repos/%s/zipball";

    private final RequestSender requestSender;

    public GitHubClient(RequestSender requestSender) {
        this.requestSender = requestSender;
    }

    public byte[] downloadRepoAsZip(String repo, String apiKey) {
        String url = format(BCHECK_REPO_ZIP_URL_TEMPLATE, repo);

        Map<String, String> headers = apiKey != null && !apiKey.isBlank() ?
                Map.of("Authorization", "Bearer " + apiKey) :
                emptyMap();

        return requestSender.sendRequest(url, headers).body().getBytes();
    }
}