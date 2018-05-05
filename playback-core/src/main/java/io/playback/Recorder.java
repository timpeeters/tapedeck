package io.playback;

import io.playback.client.HttpClient;
import io.playback.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Recorder {
    private final List<Recording> recordings = new ArrayList<>();
    private final RequestMatcher matcher;
    private final HttpClient httpClient;

    public Recorder(RequestMatcher matcher, HttpClient httpClient) {
        this.matcher = matcher;
        this.httpClient = httpClient;
    }

    public void record(Request request, Response response) {
        recordings.add(new Recording(request, response));
    }

    public Response record(Request request) {
        return seek(request).orElseGet(() -> {
            Response response = httpClient.execute(request);

            record(request, response);

            return response;
        });
    }

    public Response replay(Request request) {
        return seek(request).orElseThrow(NoMatchingRecordingFound::new);
    }

    private Optional<Response> seek(Request request) {
        return recordings.stream()
                .filter(rec -> matcher.matches(rec.getRequest(), request))
                .findFirst()
                .map(Recording::getResponse);
    }
}
