package io.tapedeck;

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
    private final Map<String, String> headers;
    private final byte[] body;

    private Request(Builder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return Arrays.copyOf(body, body.length);
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
        try {
            return new Builder().method(RequestMethod.POST).uri(new URI(""));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static class Builder {
        private RequestMethod method;
        private URI uri;
        private final Map<String, String> headers = new LinkedHashMap<>();
        private byte[] body;

        public Builder method(RequestMethod method) {
            this.method = method;

            return this;
        }

        public Builder uri(URI uri) {
            this.uri = uri;

            return this;
        }

        public Builder header(String key, String value) {
            headers.put(key, value);

            return this;
        }

        public Builder header(Consumer<HeaderBuilder> consumer) {
            HeaderBuilder builder = new HeaderBuilder();

            consumer.accept(builder);

            Header header = builder.build();

            headers.put(header.getName(), header.getValue());

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

    public static class Header {
        private final String name;
        private final String value;

        private Header(HeaderBuilder builder) {
            this.name = builder.name;
            this.value = builder.value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    public static class HeaderBuilder {
        private String name;
        private String value;

        public HeaderBuilder withName(String name) {
            this.name = name;

            return this;
        }

        public HeaderBuilder withValue(String value) {
            this.value = value;

            return this;
        }

        public Header build() {
            return new Header(this);
        }
    }
}
