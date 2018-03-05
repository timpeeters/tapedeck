package io.tapedeck;

import java.util.ArrayList;
import java.util.List;

public class Recorder {
    private final List<Recording> recordings = new ArrayList<>();

    public void record(Request request, Response response) {
        recordings.add(new Recording(request, response));
    }

    public Response replay(Request request) {
        return recordings.stream()
                .filter(RequestPredicates.requestMethod(request)
                        .and(RequestPredicates.uri(request))
                        .and(RequestPredicates.headers(request))
                        .and(RequestPredicates.body(request)))
                .findFirst().orElseThrow(NoMatchingRecordingFound::new).getResponse();
    }
}
