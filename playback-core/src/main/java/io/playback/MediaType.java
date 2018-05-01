package io.playback;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MediaType implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PARAM_CHARSET = "charset";

    private final String type;
    private final String subtype;
    private final Map<String, String> parameters;

    private MediaType(String type, String subtype, Map<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public static MediaType of(String type, String subtype) {
        return new MediaType(type, subtype, Collections.emptyMap());
    }

    public static MediaType of(String type, String subtype, Map<String, String> parameters) {
        return new MediaType(type, subtype, parameters);
    }

    public static MediaType parse(String mediaType) {
        return MediaTypeParser.parse(mediaType);
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Charset getCharset() {
        String charset = getParameter(PARAM_CHARSET);

        return charset == null ? null : Charset.forName(charset);
    }

    public MediaType param(String name, String value) {
        Map<String, String> params = new HashMap<>(parameters);
        params.put(name, value);

        return MediaType.of(type, subtype, params);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof MediaType)) {
            return false;
        }

        MediaType otherMediaType = (MediaType) otherObject;

        return Objects.equals(type, otherMediaType.type) &&
                Objects.equals(subtype, otherMediaType.subtype) &&
                Objects.equals(parameters, otherMediaType.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subtype, parameters);
    }

    @Override
    public String toString() {
        return type + '/' + subtype;
    }
}
