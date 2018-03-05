package io.tapedeck.util;

public abstract class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(Object string) {
        return string == null || "".equals(string);
    }
}
