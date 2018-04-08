package io.playback;

import io.playback.util.StringUtils;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class MediaType implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PARAM_CHARSET = "charset";

    private final String type;
    private final String subtype;
    private final Map<String, String> parameters;

    public MediaType(String type, String subtype) {
        this(type, subtype, Collections.emptyMap());
    }

    public MediaType(String type, String subtype, Map<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }

    public static MediaType parse(String mediaType) {
        if (StringUtils.isEmpty(mediaType)) {
            throw new IllegalArgumentException("'mediatype' must not be empty");
        }

        String fullType = parseFullType(mediaType);
        String type = fullType.split("/")[0];
        String subtype = fullType.split("/")[1];

        return new MediaType(type, subtype, parseParameters(mediaType));
    }

    private static String parseFullType(String mediaType) {
        String fullType = mediaType.split(";")[0].trim();

        if ("*".equals(fullType)) {
            return "*/*";
        }

        return fullType;
    }

    private static Map<String, String> parseParameters(String mediaType) {
        return Arrays.stream(mediaType.split(";"))
                .skip(1)
                .filter(p -> p.contains("="))
                .map(p -> p.split("="))
                .collect(Collectors.toMap(p -> p[0].trim(), p -> p[1].trim()));
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Charset getCharset() {
        String charset = getParameter(PARAM_CHARSET);

        return charset == null ? null : Charset.forName(charset);
    }

    @Override
    public String toString() {
        return type + '/' + subtype;
    }
}
