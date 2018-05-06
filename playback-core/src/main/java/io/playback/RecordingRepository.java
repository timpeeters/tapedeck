package io.playback;

import java.util.stream.Stream;

public interface RecordingRepository {
    void add(Recording recording);

    Stream<Recording> find();
}
