package io.playback.matcher;

import io.playback.Request;

import java.util.Arrays;

public class BodyMatcher implements RequestMatcher {
    @Override
    public boolean matches(Request request, Request otherRequest) {
        return Arrays.equals(request.getBody(), otherRequest.getBody());
    }
}
