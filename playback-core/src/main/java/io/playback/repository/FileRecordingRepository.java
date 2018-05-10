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
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileRecordingRepository implements RecordingRepository {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final Path dir;
    private final Marshaller marshaller;
    private final FileSystem fileSystem;

    public FileRecordingRepository(Path dir, Marshaller marshaller) {
        this(dir, marshaller, new DefaultFileSystem());
    }

    public FileRecordingRepository(Path dir, Marshaller marshaller, FileSystem fileSystem) {
        this.dir = dir;
        this.marshaller = marshaller;
        this.fileSystem = fileSystem;
    }

    @Override
    public void add(Recording recording) {
        try (BufferedWriter w = fileSystem.newBufferedWriter(dir.resolve(UUID.randomUUID().toString()))) {
            marshaller.marshal(recording, w);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<Recording> find() {
        try (Stream<Path> files = fileSystem.list(dir)) {
            return files.map(unmarshalFileIntoRecording()).collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Function<Path, Recording> unmarshalFileIntoRecording() {
        return path -> {
            try (BufferedReader r = fileSystem.newBufferedReader(path)) {
                return marshaller.unmarshal(r);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    public interface FileSystem {
        default BufferedReader newBufferedReader(Path path) throws IOException {
            return Files.newBufferedReader(path, DEFAULT_CHARSET);
        }

        default BufferedWriter newBufferedWriter(Path file) throws IOException {
            return Files.newBufferedWriter(file, DEFAULT_CHARSET);
        }

        default Stream<Path> list(Path dir) throws IOException {
            return Files.list(dir);
        }
    }

    private static class DefaultFileSystem implements FileSystem {
    }
}
