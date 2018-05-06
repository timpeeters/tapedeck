package io.playback.repository;

import io.playback.Recording;
import io.playback.marshaller.Marshaller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

public class FileRecordingRepository implements RecordingRepository {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final Path dir;
    private final Marshaller marshaller;

    public FileRecordingRepository(Path dir, Marshaller marshaller) {
        this.dir = dir;
        this.marshaller = marshaller;
    }

    @Override
    public void add(Recording recording) {
        try (BufferedWriter w = Files.newBufferedWriter(dir.resolve(UUID.randomUUID().toString()), DEFAULT_CHARSET)) {
            marshaller.marshal(recording, w);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Stream<Recording> find() {
        try {
            return Files.list(dir).map(unmarshalFileIntoRecording());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Function<Path, Recording> unmarshalFileIntoRecording() {
        return path -> {
            try (BufferedReader r = Files.newBufferedReader(path, DEFAULT_CHARSET)) {
                return marshaller.unmarshal(r);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
}
