package io.playback.matcher;

import io.playback.Request;

public interface RequestMatcher {
    boolean matches(Request request, Request otherRequest);
}
