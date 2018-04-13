package io.playback;

import io.playback.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

public class Recorder {
    private final List<Recording> recordings = new ArrayList<>();
    private final RequestMatcher matcher;

    public Recorder(RequestMatcher matcher) {
        this.matcher = matcher;
    }

    public void record(Request request, Response response) {
        recordings.add(new Recording(request, response));
    }

    public Response replay(Request request) {
        return recordings.stream()
                .filter(rec -> matcher.matches(rec.getRequest(), request))
                .findFirst()
                .orElseThrow(NoMatchingRecordingFound::new)
                .getResponse();
    }
}
