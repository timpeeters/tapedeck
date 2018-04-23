package io.playback.servlet;

import io.playback.Request;
import io.playback.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

final class RequestMapper {
    private RequestMapper() {
    }

    public static Request map(HttpServletRequest req) {
        return Request.builder()
                .method(RequestMethod.valueOf(req.getMethod()))
                .uri(URI.create(req.getRequestURI()))
                .build();
    }
}
