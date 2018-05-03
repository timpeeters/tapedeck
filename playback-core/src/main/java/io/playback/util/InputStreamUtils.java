package io.playback.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class InputStreamUtils {
    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;
    private static final int EOF = -1;

    private InputStreamUtils() {
    }

    /**
     * Reads all bytes from an input stream into a byte array. Does not close the stream.
     *
     * @param input the input stream to read from
     * @return a byte array containing all the bytes from the stream
     * @throws IOException if an I/O error occurs
     */
    public static byte[] toByteArray(final InputStream input) throws IOException {
        Assert.notNull(input);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);

        return output.toByteArray();
    }

    private static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = createBuffer();
        int bytesRead;

        while ((bytesRead = input.read(buffer)) != EOF) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private static byte[] createBuffer() {
        return new byte[DEFAULT_BUFFER_SIZE];
    }
}
