package io.playback.servlet.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public final class UrlDecoder {
    private static final String UTF8_CHAR_ENCODING = "UTF-8";

    private UrlDecoder() {
    }

    public static String decode(String value) {
        return decode(value, UTF8_CHAR_ENCODING);
    }

    static String decode(String value, String charset) {
        try {
            return URLDecoder.decode(value, charset);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(String.format("Unsupported charset %s", charset), e);
        }
    }
}
