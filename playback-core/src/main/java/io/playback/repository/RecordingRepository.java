package io.playback.repository;

import io.playback.Recording;

import java.util.List;

public interface RecordingRepository {
    void add(Recording recording);

    List<Recording> find();
}
