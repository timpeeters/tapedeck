package io.playback.servlet;

import io.playback.QueryParam;
import io.playback.Request;
import io.playback.RequestMethod;
import io.playback.servlet.util.UrlDecoder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    private static List<QueryParam> mapQueryParams(HttpServletRequest req) {
        if (req.getQueryString() == null || req.getQueryString().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(req.getQueryString().split("&"))
                .map(mapQueryParam())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(QueryParam::getName, Function.identity(), mergeQueryParams()),
                        m -> new ArrayList<>(m.values())));
    }

    private static Function<String, QueryParam> mapQueryParam() {
        return s -> {
            String[] parts = s.split("=");
            String name = decode(parts[0]);

            return parts.length > 1 ? QueryParam.queryParam(name, decode(parts[1])) : QueryParam.empty(name);
        };
    }

    private static BinaryOperator<QueryParam> mergeQueryParams() {
        return (a, b) -> QueryParam.queryParam(a.getName(), Stream.concat(
                a.getValues().stream(), b.getValues().stream()).toArray(String[]::new));
    }

    private static String decode(String value) {
        return UrlDecoder.decode(value);
    }
}
