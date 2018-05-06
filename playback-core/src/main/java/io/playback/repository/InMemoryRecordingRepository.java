package io.playback.repository;

import io.playback.Recording;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InMemoryRecordingRepository implements RecordingRepository {
    private final List<Recording> recordings = new ArrayList<>();

    @Override
    public void add(Recording recording) {
        recordings.add(recording);
    }

    @Override
    public Stream<Recording> find() {
        return recordings.stream();
    }
}
