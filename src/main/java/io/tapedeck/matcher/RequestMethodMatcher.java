package io.tapedeck.matcher;

import io.tapedeck.Request;

import java.util.Objects;

public class RequestMethodMatcher implements RequestMatcher {
    @Override
    public boolean matches(Request request, Request otherRequest) {
        return Objects.equals(request.getMethod(), otherRequest.getMethod());
    }
}
