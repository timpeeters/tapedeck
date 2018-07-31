package io.playback.matcher;

import io.playback.Request;

import java.util.Arrays;

public class BodyMatcher implements RequestMatcher {
    @Override
    public Result matches(Request request, Request otherRequest) {
        return Result.of(Arrays.equals(request.getBody(), otherRequest.getBody()));
    }
}
