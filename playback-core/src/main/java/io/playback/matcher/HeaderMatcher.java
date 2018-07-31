package io.playback.matcher;

import io.playback.Request;

public class HeaderMatcher implements RequestMatcher {
    private final String headerName;

    public HeaderMatcher(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        return Result.of(request.getHeader(headerName).equals(otherRequest.getHeader(headerName)));
    }
}
