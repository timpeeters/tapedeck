package io.playback;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Request {
    private final RequestMethod method;
    private final URI uri;
    private final Map<String, String> queryParams;
    private final Map<String, Header> headers;
    private final byte[] body;

    private Request(Builder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.queryParams = builder.queryParams;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Map<String, Header> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body == null ? null : Arrays.copyOf(body, body.length);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder get() {
        return get("");
    }

    public static Builder get(String uri) {
        try {
            return builder().method(RequestMethod.GET).uri(new URI(uri));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static Builder post() {
        return post("");
    }

    public static Builder post(String uri) {
        try {
            return new Builder().method(RequestMethod.POST).uri(new URI(uri));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static class Builder {
        private RequestMethod method;
        private URI uri;
        private final Map<String, String> queryParams = new LinkedHashMap<>();
        private final Map<String, Header> headers = new LinkedHashMap<>();
        private byte[] body;

        public Builder method(RequestMethod method) {
            this.method = method;

            return this;
        }

        public Builder uri(URI uri) {
            this.uri = uri;

            return this;
        }

        public Builder queryParam(String key, String value) {
            queryParams.put(key, value);

            return this;
        }

        public Builder header(String name, String value) {
            headers.put(name, Header.builder().withName(name).withValue(value).build());

            return this;
        }

        public Builder header(Consumer<Header.Builder> consumer) {
            Header.Builder builder = Header.builder();

            consumer.accept(builder);

            Header header = builder.build();

            headers.put(header.getName(), header);

            return this;
        }

        public Builder body(String body) {
            this.body = body.getBytes(Charset.forName("UTF-8"));

            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
