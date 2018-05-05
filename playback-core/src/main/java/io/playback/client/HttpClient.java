package io.playback.client;

import io.playback.Request;
import io.playback.Response;

/**
 * Executes HTTP {@link Request requests} and returns HTTP {@link Response responses}. Implementations are expected to
 * be thread-safe.
 */
public interface HttpClient {
    Response execute(Request request);
}
