package client;

import network.RequestSender;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.lang.String.format;
import static java.util.Collections.emptyMap;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class GitHubClient implements Closeable {
    private static final String BCHECK_REPO_ZIP_URL_TEMPLATE = "https://api.github.com/repos/%s/zipball";

    private final RequestSender requestSender;
    private final ExecutorService executorService;

    public GitHubClient(RequestSender requestSender) {
        this.requestSender = requestSender;
        this.executorService = newSingleThreadExecutor();
    }

    public byte[] downloadRepoAsZip(String repo, String apiKey) {
        String url = format(BCHECK_REPO_ZIP_URL_TEMPLATE, repo);

        Map<String, String> headers = apiKey != null && !apiKey.isBlank() ?
                Map.of("Authorization", "Bearer " + apiKey) :
                emptyMap();

        try {
            return executorService.submit(() -> requestSender.sendRequest(url, headers).body().getBytes()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        executorService.shutdownNow();
    }
}