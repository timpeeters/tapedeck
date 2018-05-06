package io.playback;

import io.playback.client.DefaultHttpClient;
import io.playback.client.HttpClient;
import io.playback.matcher.RequestMatcher;
import io.playback.matcher.RequestMatchers;
import io.playback.repository.InMemoryRecordingRepository;
import io.playback.repository.RecordingRepository;

public class Configuration {
    private final RequestMatcher matcher;
    private final RecordingRepository recordingRepository;
    private final HttpClient httpClient;

    private Configuration(Builder builder) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RequestMatcher matcher = RequestMatchers.DEFAULT;
        private RecordingRepository recordingRepository = new InMemoryRecordingRepository();
        private HttpClient httpClient = new DefaultHttpClient();

        public Builder matcher(RequestMatcher matcher) {
            this.matcher = matcher;

            return this;
        }

        public Builder recordingRepository(RecordingRepository recordingRepository) {
            this.recordingRepository = recordingRepository;

            return this;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;

            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}