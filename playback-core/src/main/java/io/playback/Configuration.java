package io.playback;

import io.playback.client.HttpClient;
import io.playback.matcher.RequestMatcher;
import io.playback.repository.RecordingRepository;

public class Configuration {
    private final RequestMatcher matcher;
    private final RecordingRepository recordingRepository;
    private final HttpClient httpClient;

    Configuration(ConfigurationBuilder builder) {
        this.matcher = builder.matcher;
        this.recordingRepository = builder.recordingRepository;
        this.httpClient = builder.httpClient;
    }

    public RequestMatcher matcher() {
        return matcher;
    }

    public RecordingRepository recordingRepository() {
        return recordingRepository;
    }

    public HttpClient httpClient() {
        return httpClient;
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }
}
