package io.playback.util;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class InputStreamUtilsTest {
    private static final byte[] EMPTY_BYTES = newPreFilledByteArray(0);
    private static final byte[] SMALL_BYTES = newPreFilledByteArray(100);
    private static final byte[] LARGE_BYTES = newPreFilledByteArray(100_000);

    @Test
    void toByteArray_empty() throws IOException {
        assertThat(InputStreamUtils.toByteArray(new ByteArrayInputStream(EMPTY_BYTES))).isEqualTo(EMPTY_BYTES);
    }

    @Test
    void toByteArray_small() throws IOException {
        assertThat(InputStreamUtils.toByteArray(new ByteArrayInputStream(SMALL_BYTES))).isEqualTo(SMALL_BYTES);
    }

    @Test
    void toByteArray_large() throws IOException {
        assertThat(InputStreamUtils.toByteArray(new ByteArrayInputStream(LARGE_BYTES))).isEqualTo(LARGE_BYTES);
    }

    private static byte[] newPreFilledByteArray(int size) {
        byte[] array = new byte[size];

        for (int i = 0; i < size; i++) {
            array[i] = (byte) i;
        }

        return array;
    }
}
