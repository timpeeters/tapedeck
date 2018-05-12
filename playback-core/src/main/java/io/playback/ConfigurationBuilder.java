package io.playback;

import io.playback.client.DefaultHttpClient;
import io.playback.client.HttpClient;
import io.playback.matcher.RequestMatcher;
import io.playback.matcher.RequestMatchers;
import io.playback.repository.InMemoryRecordingRepository;
import io.playback.repository.RecordingRepository;

public class ConfigurationBuilder {
    RequestMatcher matcher = RequestMatchers.DEFAULT;
    RecordingRepository recordingRepository = new InMemoryRecordingRepository();
    HttpClient httpClient = new DefaultHttpClient();

    public ConfigurationBuilder matcher(RequestMatcher matcher) {
        this.matcher = matcher;

        return this;
    }

    public ConfigurationBuilder recordingRepository(RecordingRepository recordingRepository) {
        this.recordingRepository = recordingRepository;

        return this;
    }

    public ConfigurationBuilder httpClient(HttpClient httpClient) {
        this.httpClient = httpClient;

        return this;
    }

    public Configuration build() {
        return new Configuration(this);
    }
}
