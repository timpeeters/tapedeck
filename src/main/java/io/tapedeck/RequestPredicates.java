package io.tapedeck;

import java.util.function.Predicate;

public class RequestPredicates {
    private RequestPredicates() {
    }

    public static Predicate<Recording> requestMethod(Request request) {
        return rec -> rec.getRequest().getMethod().equals(request.getMethod());
    }

    public static Predicate<Recording> uri(Request request) {
        return rec -> rec.getRequest().getUri().equals(request.getUri());
    }

    public static Predicate<Recording> headers(Request request) {
        return rec -> rec.getRequest().getHeaders().equals(request.getHeaders());
    }
}
