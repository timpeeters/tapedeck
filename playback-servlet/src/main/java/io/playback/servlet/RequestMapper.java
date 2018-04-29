package io.playback.servlet;

import io.playback.Header;
import io.playback.Request;
import io.playback.RequestMethod;
import io.playback.servlet.util.QueryStringParser;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;
import java.util.function.Function;

final class RequestMapper {
    private RequestMapper() {
    }

    public static Request map(HttpServletRequest req) {
        return Request.builder()
                .method(RequestMethod.valueOf(req.getMethod()))
                .uri(URI.create(req.getRequestURI()))
                .queryParams(QueryStringParser.parse(req.getQueryString()))
                .headers(mapHeaders(req))
                .build();
    }

    private static Header[] mapHeaders(HttpServletRequest req) {
        return Collections.list(req.getHeaderNames()).stream()
                .map(mapHeader(req))
                .toArray(Header[]::new);
    }

    private static Function<String, Header> mapHeader(HttpServletRequest req) {
        return name -> Header.builder()
                .withName(name)
                .withValues(Collections.list(req.getHeaders(name)).toArray(new String[0]))
                .build();
    }
}
