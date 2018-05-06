package io.playback.repository;

import io.playback.Recording;
import io.playback.Request;
import io.playback.Response;
import io.playback.marshaller.Marshaller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith({MockitoExtension.class, TempDirectoryExtension.class})
public class FileRecordingRepositoryTest {
    @Mock
    private Marshaller mockMarshaller;

    @Test
    public void find_emptyRepository(@TempDirectory Path dir) {
        assertThat(new FileRecordingRepository(dir, mockMarshaller).find()).isEmpty();
    }

    @Test
    public void find_oneRecording(@TempDirectory Path dir) {
        FileRecordingRepository repository = new FileRecordingRepository(dir, mockMarshaller);
        repository.add(new Recording(Request.get("/").build(), Response.ok().build()));

        assertThat(repository.find()).hasSize(1);
    }

    @Test
    public void find_multipleRecordings(@TempDirectory Path dir) {
        FileRecordingRepository repository = new FileRecordingRepository(dir, mockMarshaller);
        repository.add(new Recording(Request.get("/a").build(), Response.ok().build()));
        repository.add(new Recording(Request.get("/b").build(), Response.ok().build()));

        assertThat(repository.find()).hasSize(2);
    }
}
