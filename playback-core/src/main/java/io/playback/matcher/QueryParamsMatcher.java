package io.playback.matcher;

import io.playback.Request;

import java.util.Objects;

public class QueryParamsMatcher implements RequestMatcher {
    @Override
    public boolean matches(Request request, Request otherRequest) {
        return Objects.equals(request.getQueryParams(), otherRequest.getQueryParams());
    }
}
