package io.playback.servlet;

import io.playback.Request;
import io.playback.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.BinaryOperator;
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
                .collect(Collectors.toMap(a -> a[0], a -> new String[]{a[1]}, mergeQueryParamValues()));
    }

    private static BinaryOperator<String[]> mergeQueryParamValues() {
        return (a, b) -> Stream.concat(Arrays.stream(a), Arrays.stream(b)).toArray(String[]::new);
    }
}
