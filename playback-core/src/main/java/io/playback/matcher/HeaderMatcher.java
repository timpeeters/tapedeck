package io.playback.matcher;

import io.playback.Request;

import java.util.stream.Stream;

public class HeaderMatcher implements RequestMatcher {
    private final String headerName;

    public HeaderMatcher(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        return Result.aggregate(headerValues(request, otherRequest)
                .map(v -> Result.of(containsValue(request, v) && containsValue(otherRequest, v)))
                .toArray(Result[]::new));
    }

    private boolean containsValue(Request request, String headerValue) {
        return request.getHeader(headerName).map(h -> h.containsValue(headerValue)).orElse(false);
    }

    private Stream<String> headerValues(Request request, Request otherRequest) {
        return Stream.concat(
                request.getHeader(headerName).map(h -> h.getValues().stream()).orElse(Stream.empty()),
                otherRequest.getHeader(headerName).map(h -> h.getValues().stream()).orElse(Stream.empty()))
                .distinct();
    }
}
