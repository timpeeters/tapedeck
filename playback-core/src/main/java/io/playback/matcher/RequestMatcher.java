package io.playback.matcher;

import io.playback.Request;

@FunctionalInterface
public interface RequestMatcher {
    boolean matches(Request request, Request otherRequest);
}
