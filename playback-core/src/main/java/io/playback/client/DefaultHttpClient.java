package io.playback.client;

import io.playback.Header;
import io.playback.Request;
import io.playback.Response;
import io.playback.util.InputStreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Default implementation of {@link HttpClient} that relies on the JDK {@link HttpURLConnection}.
 */
public class DefaultHttpClient implements HttpClient {
    private static final int CONNECT_TIMEOUT_MILLIS = 5_000;
    private static final int READ_TIMEOUT_MILLIS = 10_000;

    @Override
    public Response execute(Request request) {
        try {
            HttpURLConnection connection = send(request);

            return createResponse(connection);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private HttpURLConnection send(Request request) throws IOException {
        HttpURLConnection connection = createConnection(request);
        connection.setRequestMethod(request.getMethod().name());
        writeHeaders(connection, request);
        connection.connect();

        return connection;
    }

    private void writeHeaders(HttpURLConnection connection, Request request) {
        request.getHeaders().values()
                .forEach(h -> h.getValues()
                        .forEach(v -> connection.setRequestProperty(h.getName(), v)));
    }

    private Response createResponse(HttpURLConnection connection) throws IOException {
        return Response.builder()
                .statusCode(Integer.toString(connection.getResponseCode()))
                .statusText(connection.getResponseMessage())
                .headers(readHeaders(connection))
                .body(readBody(connection))
                .build();
    }

    private Header[] readHeaders(HttpURLConnection connection) {
        return connection.getHeaderFields().entrySet().stream()
                .map(e -> Header.header(e.getKey(), e.getValue().toArray(new String[] {})))
                .toArray(Header[]::new);
    }

    private byte[] readBody(HttpURLConnection connection) throws IOException {
        try (InputStream in = connection.getInputStream()) {
            return InputStreamUtils.toByteArray(in);
        }
    }

    private HttpURLConnection createConnection(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) createUrl(request).openConnection();
        configureConnection(connection);

        return connection;
    }

    private void configureConnection(HttpURLConnection connection) {
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
        connection.setInstanceFollowRedirects(false);
        connection.setReadTimeout(READ_TIMEOUT_MILLIS);
    }

    private URL createUrl(Request request) {
        try {
            return request.getUri().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("Malformed URL: %s", request.getUri()), e);
        }
    }
}
