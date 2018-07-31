package io.playback.matcher;

import io.playback.Request;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class RequestMatchers implements RequestMatcher {
    public static final RequestMatcher DEFAULT = of(requestMethod(), uri(), queryParams(), headers(), body());

    private final List<RequestMatcher> matchers;

    private RequestMatchers(List<RequestMatcher> matchers) {
        this.matchers = Collections.unmodifiableList(matchers);
    }

    public static RequestMatcher of(RequestMatcher... matchers) {
        return new RequestMatchers(Arrays.asList(matchers));
    }

    public static RequestMatcher requestMethod() {
        return new RequestMethodMatcher();
    }

    public static RequestMatcher uri() {
        return new UriMatcher();
    }

    public static RequestMatcher queryParams() {
        return new QueryParamsMatcher();
    }

    public static RequestMatcher headers() {
        return new HeadersMatcher();
    }

    public static RequestMatcher body() {
        return new BodyMatcher();
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        return Result.of(matchers.stream().allMatch(matcher -> matcher.matches(request, otherRequest).isExactMatch()));
    }
}
