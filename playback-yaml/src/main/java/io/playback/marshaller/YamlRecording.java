package io.playback.marshaller;

import io.playback.Recording;

public class YamlRecording {
    private YamlRequest request;
    private YamlResponse response;

    YamlRecording() {
        this(null, null);
    }

    YamlRecording(YamlRequest request, YamlResponse response) {
        this.request = request;
        this.response = response;
    }

    static YamlRecording from(Recording recording) {
        return new YamlRecording(YamlRequest.from(recording.getRequest()), YamlResponse.from(recording.getResponse()));
    }

    static Recording map(YamlRecording yaml) {
        return new Recording(yaml.request.map(), yaml.response.unmarshal());
    }
}
