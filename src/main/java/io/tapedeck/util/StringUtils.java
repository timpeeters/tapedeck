package io.tapedeck.util;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(Object string) {
        return string == null || "".equals(string);
    }
}
