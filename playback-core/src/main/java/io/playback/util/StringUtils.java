package io.playback.util;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(Object string) {
        return string == null || "".equals(string);
    }
}
