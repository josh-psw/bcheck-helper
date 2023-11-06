package client;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;
import client.github.GitHubClient;
import network.RequestSender;
import org.junit.jupiter.api.Test;
import settings.github.GitHubSettingsReader;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Collections.emptyMap;
import static org.mockito.Mockito.*;

class GitHubClientTest {
    private final RequestSender requestSender = mock(RequestSender.class);
    private final Logging logger = mock(Logging.class);
    private final GitHubSettingsReader gitHubSettingsReader = mock(GitHubSettingsReader.class);
    private final GitHubClient gitHubClient = new GitHubClient(requestSender, gitHubSettingsReader, logger);

    @Test
    void givenRepoAndApiKey_whenDownloadRepo_thenCorrectUrl_withCorrectAuthorizationHeader_requested() {
        String repo = "owner/repo";
        String apiKey = "abcde";

        HttpResponse response = mock(HttpResponse.class);
        when(response.body()).thenReturn(createEmptyByteArray());
        when(requestSender.makeGetRequest(anyString(), anyMap())).thenReturn(response);
        when(gitHubSettingsReader.apiKey()).thenReturn(apiKey);

        gitHubClient.downloadRepoAsZip(repo);

        verify(requestSender).makeGetRequest("https://api.github.com/repos/" + repo + "/zipball", Map.of(
                "Authorization", "Bearer " + apiKey
        ));
    }

    @Test
    void givenRepoWithNullApiKey_whenDownloadRepo_thenCorrectUrl_withNoHeaders_requested() {
        String repo = "owner/repo";

        HttpResponse response = mock(HttpResponse.class);
        when(response.body()).thenReturn(createEmptyByteArray());
        when(requestSender.makeGetRequest(anyString(), anyMap())).thenReturn(response);
        when(gitHubSettingsReader.apiKey()).thenReturn(null);

        gitHubClient.downloadRepoAsZip(repo);

        verify(requestSender).makeGetRequest("https://api.github.com/repos/" + repo + "/zipball", emptyMap());
    }

    @Test
    void givenRepoWithBlankApiKey_whenDownloadRepo_thenCorrectUrl_withNoHeaders_requested() {
        String repo = "owner/repo";

        HttpResponse response = mock(HttpResponse.class);
        when(response.body()).thenReturn(createEmptyByteArray());
        when(requestSender.makeGetRequest(anyString(), anyMap())).thenReturn(response);
        when(gitHubSettingsReader.apiKey()).thenReturn("");

        gitHubClient.downloadRepoAsZip(repo);

        verify(requestSender).makeGetRequest("https://api.github.com/repos/" + repo + "/zipball", emptyMap());
    }

    private static ByteArray createEmptyByteArray() {
        return new ByteArray() {
            @Override
            public byte getByte(int index) {
                return 0;
            }

            @Override
            public void setByte(int index, byte value) {

            }

            @Override
            public void setByte(int index, int value) {

            }

            @Override
            public void setBytes(int index, byte... data) {

            }

            @Override
            public void setBytes(int index, int... data) {

            }

            @Override
            public void setBytes(int index, ByteArray byteArray) {

            }

            @Override
            public int length() {
                return 0;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public ByteArray subArray(int startIndexInclusive, int endIndexExclusive) {
                return null;
            }

            @Override
            public ByteArray subArray(Range range) {
                return null;
            }

            @Override
            public ByteArray copy() {
                return null;
            }

            @Override
            public ByteArray copyToTempFile() {
                return null;
            }

            @Override
            public int indexOf(ByteArray searchTerm) {
                return 0;
            }

            @Override
            public int indexOf(String searchTerm) {
                return 0;
            }

            @Override
            public int indexOf(ByteArray searchTerm, boolean caseSensitive) {
                return 0;
            }

            @Override
            public int indexOf(String searchTerm, boolean caseSensitive) {
                return 0;
            }

            @Override
            public int indexOf(ByteArray searchTerm, boolean caseSensitive, int startIndexInclusive, int endIndexExclusive) {
                return 0;
            }

            @Override
            public int indexOf(String searchTerm, boolean caseSensitive, int startIndexInclusive, int endIndexExclusive) {
                return 0;
            }

            @Override
            public int indexOf(Pattern pattern) {
                return 0;
            }

            @Override
            public int indexOf(Pattern pattern, int startIndexInclusive, int endIndexExclusive) {
                return 0;
            }

            @Override
            public int countMatches(ByteArray searchTerm) {
                return 0;
            }

            @Override
            public int countMatches(String searchTerm) {
                return 0;
            }

            @Override
            public int countMatches(ByteArray searchTerm, boolean caseSensitive) {
                return 0;
            }

            @Override
            public int countMatches(String searchTerm, boolean caseSensitive) {
                return 0;
            }

            @Override
            public int countMatches(ByteArray searchTerm, boolean caseSensitive, int startIndexInclusive, int endIndexExclusive) {
                return 0;
            }

            @Override
            public int countMatches(String searchTerm, boolean caseSensitive, int startIndexInclusive, int endIndexExclusive) {
                return 0;
            }

            @Override
            public int countMatches(Pattern pattern) {
                return 0;
            }

            @Override
            public int countMatches(Pattern pattern, int startIndexInclusive, int endIndexExclusive) {
                return 0;
            }

            @Override
            public ByteArray withAppended(byte... data) {
                return null;
            }

            @Override
            public ByteArray withAppended(int... data) {
                return null;
            }

            @Override
            public ByteArray withAppended(String text) {
                return null;
            }

            @Override
            public ByteArray withAppended(ByteArray byteArray) {
                return null;
            }

            @Override
            public Iterator<Byte> iterator() {
                return null;
            }
        };
    }
}