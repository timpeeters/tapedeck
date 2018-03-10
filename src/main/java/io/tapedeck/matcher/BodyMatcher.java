package io.tapedeck.matcher;

import io.tapedeck.Request;

import java.util.Arrays;

public class BodyMatcher implements RequestMatcher {
    @Override
    public boolean matches(Request request, Request otherRequest) {
        return Arrays.equals(request.getBody(), otherRequest.getBody());
    }
}
