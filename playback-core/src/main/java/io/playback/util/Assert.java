package io.playback.util;

public final class Assert {
    private Assert() {
    }

    public static void notEmpty(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("must not be null");
        }
    }
}
