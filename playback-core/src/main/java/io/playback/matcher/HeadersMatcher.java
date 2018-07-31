package io.playback.matcher;

import io.playback.Request;

import java.util.stream.Stream;

public class HeadersMatcher implements RequestMatcher {
    @Override
    public Result matches(Request request, Request otherRequest) {
        return Result.aggregate(headerNames(request, otherRequest)
                .map(headerName -> new HeaderMatcher(headerName).matches(request, otherRequest))
                .toArray(Result[]::new));
    }

    private Stream<String> headerNames(Request request, Request otherRequest) {
        return Stream.concat(request.getHeaders().keySet().stream(), otherRequest.getHeaders().keySet().stream());
    }
}
