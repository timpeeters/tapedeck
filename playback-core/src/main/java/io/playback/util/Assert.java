package io.playback.util;

public final class Assert {
    private Assert() {
    }

    public static void assertNotEmpty(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }
}
