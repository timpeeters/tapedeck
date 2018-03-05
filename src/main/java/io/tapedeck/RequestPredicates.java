package io.tapedeck;

import java.util.Arrays;
import java.util.function.Predicate;

public final class RequestPredicates {
    private RequestPredicates() {
    }

    public static Predicate<Recording> requestMethod(Request request) {
        return rec -> rec.getRequest().getMethod().equals(request.getMethod());
    }

    public static Predicate<Recording> uri(Request request) {
        return rec -> rec.getRequest().getUri().equals(request.getUri());
    }

    public static Predicate<Recording> queryParams(Request request) {
        return rec -> rec.getRequest().getQueryParams().equals(request.getQueryParams());
    }

    public static Predicate<Recording> headers(Request request) {
        return rec -> rec.getRequest().getHeaders().equals(request.getHeaders());
    }

    public static Predicate<Recording> body(Request request) {
        return rec -> Arrays.equals(rec.getRequest().getBody(), request.getBody());
    }
}
