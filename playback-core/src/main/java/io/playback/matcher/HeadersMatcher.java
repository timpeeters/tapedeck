package io.playback.matcher;

import io.playback.Request;

import java.util.Objects;

public class HeadersMatcher implements RequestMatcher {
    @Override
    public Result matches(Request request, Request otherRequest) {
        return Result.of(Objects.equals(request.getHeaders(), otherRequest.getHeaders()));
    }
}
