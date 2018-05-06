package io.playback.marshaller;

import io.playback.Request;
import io.playback.RequestMethod;

import java.net.URI;

class YamlRequest {
    private String method;
    private String uri;

    YamlRequest() {
        this(null, null);
    }

    YamlRequest(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    static YamlRequest from(Request request) {
        return new YamlRequest(request.getMethod().name(), request.getUri().toString());
    }

    Request map() {
        return Request.builder()
                .method(RequestMethod.valueOf(method))
                .uri(URI.create(uri))
                .build();
    }
}
