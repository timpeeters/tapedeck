package io.playback.repository;

import io.playback.Recording;

import java.util.stream.Stream;

public interface RecordingRepository {
    void add(Recording recording);

    Stream<Recording> find();
}
