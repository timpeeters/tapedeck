package io.playback.marshaller;

import io.playback.Recording;

public class YamlRecording {
    public YamlRequest request;
    public YamlResponse response;

    public YamlRecording() {
        this(null, null);
    }

    public YamlRecording(YamlRequest request, YamlResponse response) {
        this.request = request;
        this.response = response;
    }

    public static YamlRecording from(Recording recording) {
        return new YamlRecording(YamlRequest.from(recording.getRequest()), YamlResponse.from(recording.getResponse()));
    }

    public static Recording map(YamlRecording yaml) {
        return new Recording(yaml.request.map(), yaml.response.unmarshal());
    }
}
