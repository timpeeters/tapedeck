package io.playback.repository;

import io.playback.Recording;
import io.playback.Request;
import io.playback.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryRecordingRepositoryTest {
    @Test
    void emptyRepository() {
        assertThat(new InMemoryRecordingRepository().find()).isEmpty();
    }

    @Test
    void oneRecording() {
        InMemoryRecordingRepository repository = new InMemoryRecordingRepository();

        repository.add(new Recording(Request.get().build(), Response.ok().build()));

        assertThat(repository.find()).hasSize(1);
    }
}
