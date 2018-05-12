package io.playback;

import io.playback.client.HttpClient;
import io.playback.repository.RecordingRepository;

public class World {
    HttpClient httpClient;
    Playback playback;
    RecordingRepository recordingRepository;
    Response response;
}
