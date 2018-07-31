package io.playback;

import java.util.Optional;

public final class Playback {
    private final Configuration config;

    private Playback(Configuration config) {
        this.config = config;
    }

    public static Playback create() {
        return configure(Configuration.builder());
    }

    public static Playback configure(ConfigurationBuilder builder) {
        return new Playback(builder.build());
    }

    public void record(Request request, Response response) {
        config.recordingRepository().add(new Recording(request, response));
    }

    public Response play(Request request) {
        return seek(request).orElseGet(() -> {
            Response response = config.httpClient().execute(request);

            record(request, response);

            return response;
        });
    }

    public Response replay(Request request) {
        return seek(request).orElseThrow(NoMatchingRecordingFound::new);
    }

    private Optional<Response> seek(Request request) {
        return config.recordingRepository().find().stream()
                .filter(rec -> config.matcher().matches(rec.getRequest(), request).isExactMatch())
                .findFirst()
                .map(Recording::getResponse);
    }
}
