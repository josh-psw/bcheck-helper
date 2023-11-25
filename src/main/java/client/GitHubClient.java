package client;

import network.RequestSender;

import java.util.Map;

import static java.util.Collections.emptyMap;

public class GitHubClient {
    private static final String REPOSITORY_ZIPBALL_URL_TEMPLATE = "%s/repos/%s/zipball";

    private final RequestSender requestSender;

    public GitHubClient(RequestSender requestSender) {
        this.requestSender = requestSender;
    }

    public byte[] downloadRepoAsZip(String repositoryUrl, String repositoryName, String apiKey) {
        String trimmedRepositoryUrl = repositoryUrl.trim().replaceAll("/$", "");
        String url = REPOSITORY_ZIPBALL_URL_TEMPLATE.formatted(trimmedRepositoryUrl, repositoryName);

        Map<String, String> headers = apiKey != null && !apiKey.isBlank() ?
                Map.of("Authorization", "Bearer " + apiKey) :
                emptyMap();

        return requestSender.sendRequest(url, headers).body().getBytes();
    }
}