package io.tapedeck.matcher;

import io.tapedeck.Request;

public interface RequestMatcher {
    boolean matches(Request request, Request otherRequest);
}
