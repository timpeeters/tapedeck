package io.playback.repository;

import io.playback.Recording;
import io.playback.Request;
import io.playback.Response;
import io.playback.marshaller.Marshaller;
import io.playback.repository.FileRecordingRepository.FileSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, TempDirectoryExtension.class})
public class FileRecordingRepositoryTest {
    @Mock
    private Marshaller mockMarshaller;

    @Mock
    private FileSystem mockFileSystem;

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

    @Test
    public void find_listThrowsIoException(@TempDirectory Path dir) throws IOException {
        when(mockFileSystem.list(any(Path.class))).thenThrow(new IOException());

        assertThatThrownBy(() -> new FileRecordingRepository(dir, mockMarshaller, mockFileSystem).find())
                .isInstanceOf(UncheckedIOException.class);
    }

    @Test
    public void find_readThrowsIoException(@TempDirectory Path dir) throws IOException {
        when(mockFileSystem.list(any(Path.class))).thenReturn(Stream.of(mock(Path.class)));
        when(mockFileSystem.newBufferedReader(any(Path.class))).thenThrow(IOException.class);

        assertThatThrownBy(() -> new FileRecordingRepository(dir, mockMarshaller, mockFileSystem).find())
                .isInstanceOf(UncheckedIOException.class);
    }

    @Test
    public void add_writeThrowsIoException(@TempDirectory Path dir) throws IOException {
        when(mockFileSystem.newBufferedWriter(any(Path.class))).thenThrow(new IOException());

        assertThatThrownBy(() -> new FileRecordingRepository(dir, mockMarshaller, mockFileSystem)
                .add(new Recording(Request.get("/c").build(), Response.ok().build())))
                .isInstanceOf(UncheckedIOException.class);
    }
}
