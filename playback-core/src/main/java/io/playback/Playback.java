package io.playback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public final class Playback {
    private final List<Recording> recordings = new ArrayList<>();
    private final Configuration config;

    private Playback(Configuration config) {
        this.config = config;
    }

    public static Playback configure() {
        return new Playback(Configuration.builder().build());
    }

    public static Playback configure(Consumer<Configuration.Builder> consumer) {
        Configuration.Builder builder = Configuration.builder();

        consumer.accept(builder);

        return new Playback(builder.build());
    }

    public void record(Request request, Response response) {
        recordings.add(new Recording(request, response));
    }

    public Response record(Request request) {
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
        return recordings.stream()
                .filter(rec -> config.matcher().matches(rec.getRequest(), request))
                .findFirst()
                .map(Recording::getResponse);
    }
}
