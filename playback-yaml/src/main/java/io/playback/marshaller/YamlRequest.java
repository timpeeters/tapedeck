package io.playback.marshaller;

import io.playback.Request;
import io.playback.RequestMethod;

import java.net.URI;

public class YamlRequest {
    public String method;
    public String uri;

    public YamlRequest() {
        this(null, null);
    }

    public YamlRequest(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public static YamlRequest from(Request request) {
        return new YamlRequest(request.getMethod().name(), request.getUri().toString());
    }

    public Request map() {
        return Request.builder()
                .method(RequestMethod.valueOf(method))
                .uri(URI.create(uri))
                .build();
    }
}
