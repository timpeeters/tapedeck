package io.playback.client;

import io.playback.Request;
import io.playback.Response;

import java.io.IOException;

/**
 * Executes HTTP {@link Request requests} and returns HTTP {@link Response responses}. Implementations are expected to
 * be thread-safe.
 */
public interface HttpClient {
    Response execute(Request request) throws IOException;
}
