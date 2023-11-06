package network;

import burp.api.montoya.http.Http;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;

import java.util.Map;

import static burp.api.montoya.http.message.StatusCodeClass.*;
import static burp.api.montoya.http.message.requests.HttpRequest.httpRequestFromUrl;

public class RequestSender {
    private final Http http;
    private final Logging logger;

    public RequestSender(Http http, Logging logger) {
        this.http = http;
        this.logger = logger;
    }

    public HttpResponse makeGetRequest(String url, Map<String, String> headers) {
        return sendRequest("GET", url, headers, "");
    }

    public HttpResponse makePostRequest(String url, Map<String, String> headers, String body) {
        return sendRequest("POST", url, headers, body);
    }

    public HttpResponse makePutRequest(String url, Map<String, String> headers, String body) {
        return sendRequest("PUT", url, headers, body);
    }

    private HttpResponse sendRequest(String method, String url, Map<String, String> headers, String body) {
        var request = buildRequest(method, url, headers, body);

        logger.logToOutput("Requesting " + url);

        var response = http.sendRequest(request).response();

        if (response.isStatusCodeClass(CLASS_4XX_CLIENT_ERRORS) || response.isStatusCodeClass(CLASS_5XX_SERVER_ERRORS)) {
            var responseBody = response.bodyToString();
            var exceptionMessage = "Failed to make request to " + url + ". Error code: " + response.statusCode() + ". Error message: " + responseBody;

            logger.logToError(exceptionMessage);
            throw new IllegalStateException(exceptionMessage);
        } else if (response.isStatusCodeClass(CLASS_3XX_REDIRECTION)) {
            var redirectLocation = response.headerValue("Location");
            return sendRequest(method, redirectLocation, headers, body);
        } else {
            logger.logToOutput("Request to " + url + " successful");
            return response;
        }
    }

    private static HttpRequest buildRequest(String method, String url, Map<String, String> headers, String body) {
        var request = httpRequestFromUrl(url)
                .withMethod(method)
                .withBody(body);

        for (var header : headers.entrySet()) {
            request = request.withAddedHeader(header.getKey(), header.getValue());
        }

        return request;
    }
}
