package io.playback.matcher;

import io.playback.Request;

@FunctionalInterface
public interface RequestMatcher {
    Result matches(Request request, Request otherRequest);
}
