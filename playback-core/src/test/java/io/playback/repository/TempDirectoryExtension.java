package io.playback.repository;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class TempDirectoryExtension implements AfterEachCallback, Extension, ParameterResolver {
    private static final String KEY = "tempDirectory";

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Path tempDirectory = (Path) getLocalStore(context).get(KEY);

        if (tempDirectory != null) {
            delete(tempDirectory);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext param, ExtensionContext ext) throws ParameterResolutionException {
        return param.isAnnotated(TempDirectory.class) && param.getParameter().getType() == Path.class;
    }

    @Override
    public Object resolveParameter(ParameterContext param, ExtensionContext ext) throws ParameterResolutionException {
        return getLocalStore(ext).getOrComputeIfAbsent(KEY, key -> createTempDir(ext));
    }

    private Store getLocalStore(ExtensionContext context) {
        return context.getStore(localNamespace(context));
    }

    private Namespace localNamespace(ExtensionContext context) {
        return Namespace.create(TempDirectoryExtension.class, context);
    }

    private Path createTempDir(ExtensionContext context) {
        try {
            return Files.createTempDirectory(resolveTempDirName(context));
        } catch (IOException e) {
            throw new ParameterResolutionException("Could not create temp directory", e);
        }
    }

    private String resolveTempDirName(ExtensionContext context) {
        if (context.getTestMethod().isPresent()) {
            return context.getTestMethod().get().getName();
        }

        return context.getTestClass().isPresent() ? context.getTestClass().get().getName() : context.getDisplayName();
    }

    private void delete(Path tempDir) throws IOException {
        Files.walkFileTree(tempDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return deleteAndContinue(file);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return deleteAndContinue(dir);
            }

            private FileVisitResult deleteAndContinue(Path path) throws IOException {
                Files.delete(path);

                return FileVisitResult.CONTINUE;
            }
        });
    }
}
