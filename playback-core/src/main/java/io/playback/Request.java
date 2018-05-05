package io.playback;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Supplier;

public class Request {
    private final RequestMethod method;
    private final URI uri;
    private final Map<String, QueryParam> queryParams;
    private final Map<String, Header> headers;
    private final byte[] body;

    private Request(Builder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.queryParams = Collections.unmodifiableMap(builder.queryParams);
        this.headers = Collections.unmodifiableMap(builder.headers);
        this.body = builder.body;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, QueryParam> getQueryParams() {
        return queryParams;
    }

    public Map<String, Header> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body == null ? null : Arrays.copyOf(body, body.length);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof Request)) {
            return false;
        }

        Request otherRequest = (Request) otherObject;

        return Objects.equals(method, otherRequest.method) &&
                Objects.equals(uri, otherRequest.uri) &&
                Objects.equals(queryParams, otherRequest.queryParams) &&
                Objects.equals(headers, otherRequest.headers) &&
                Objects.equals(body, otherRequest.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri, queryParams, headers, body);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("method=" + method)
                .add("uri=" + uri)
                .add("queryParams=" + queryParams.values())
                .add("headers=" + headers.values())
                .toString();
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
        private final Map<String, QueryParam> queryParams = new LinkedHashMap<>();
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

        public Builder queryParam(String name, String... values) {
            queryParams.put(name, QueryParam.queryParam(name, values));

            return this;
        }

        public Builder queryParams(List<QueryParam> queryParams) {
            queryParams.forEach(queryParam -> this.queryParams.put(queryParam.getName(), queryParam));

            return this;
        }

        public Builder header(String name, String value) {
            headers.put(name, Header.header(name, value));

            return this;
        }

        public Builder header(Supplier<Header> supplier) {
            Header header = supplier.get();

            headers.put(header.getName(), header);

            return this;
        }

        public Builder headers(Header... headers) {
            Arrays.stream(headers).forEach(h -> this.headers.put(h.getName(), h));

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
