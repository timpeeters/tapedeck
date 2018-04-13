package io.playback.matcher;

import io.playback.Request;

import java.util.Objects;

public class UriMatcher implements RequestMatcher {
    @Override
    public boolean matches(Request request, Request otherRequest) {
        return Objects.equals(request.getUri(), otherRequest.getUri());
    }
}
