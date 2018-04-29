package io.playback.servlet;

import io.playback.Request;
import io.playback.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class RequestMapper {
    private RequestMapper() {
    }

    public static Request map(HttpServletRequest req) {
        return Request.builder()
                .method(RequestMethod.valueOf(req.getMethod()))
                .uri(URI.create(req.getRequestURI()))
                .queryParams(mapQueryParams(req))
                .build();
    }

    private static Map<String, String[]> mapQueryParams(HttpServletRequest req) {
        if (req.getQueryString() == null || req.getQueryString().isEmpty()) {
            return Collections.emptyMap();
        }

        return Arrays.stream(req.getQueryString().split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(queryParamKey(), queryParamValue(), mergeQueryParamValues()));
    }

    private static Function<String[], String> queryParamKey() {
        return a -> decode(a[0]);
    }

    private static Function<String[], String[]> queryParamValue() {
        return a -> new String[]{decode(a[1])};
    }

    private static BinaryOperator<String[]> mergeQueryParamValues() {
        return (a, b) -> Stream.concat(Arrays.stream(a), Arrays.stream(b)).toArray(String[]::new);
    }

    private static String decode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid encoding", e);
        }
    }
}
