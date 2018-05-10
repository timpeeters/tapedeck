package io.playback.repository;

import io.playback.Recording;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryRecordingRepository implements RecordingRepository {
    private final List<Recording> recordings = new ArrayList<>();

    @Override
    public void add(Recording recording) {
        recordings.add(recording);
    }

    @Override
    public List<Recording> find() {
        return Collections.unmodifiableList(recordings);
    }
}
