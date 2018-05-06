package io.playback;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private final String statusCode;
    private final String statusText;
    private final Map<String, List<String>> headers;
    private final byte[] body;

    private Response(Builder builder) {
        this.statusCode = builder.statusCode;
        this.statusText = builder.statusText;
        this.headers = Collections.unmodifiableMap(builder.headers);
        this.body = builder.body;
    }

    public Charset getCharset() {
        List<String> values = getHeader(Headers.CONTENT_TYPE);

        if (values != null && !values.isEmpty()) {
            return MediaType.parse(values.iterator().next()).getCharset();
        }

        return Charset.forName("UTF-8");
    }

    public List<String> getHeader(String name) {
        return headers.get(name);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getBodyAsString() {
        return new String(body, getCharset());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder ok() {
        return builder().statusCode("200").statusText("OK");
    }

    public static class Builder {
        private String statusCode;
        private String statusText;
        private Map<String, List<String>> headers = new HashMap<>();
        private byte[] body;


        public Builder like(Response response) {
            this.statusCode = response.statusCode;
            this.statusText = response.statusText;
            this.headers = response.headers;
            this.body = response.body;

            return this;
        }

        public Builder statusCode(String statusCode) {
            this.statusCode = statusCode;

            return this;
        }

        public Builder statusText(String statusText) {
            this.statusText = statusText;

            return this;
        }

        public Builder body(String body) {
            this.body = body.getBytes(Charset.forName("UTF-8"));

            return this;
        }

        public Builder body(byte[] body) {
            this.body = Arrays.copyOf(body, body.length);

            return this;
        }

        public Builder header(String name, String value) {
            List<String> values = headers.getOrDefault(name, new ArrayList<>());
            values.add(value);

            headers.put(name, values);

            return this;
        }

        public Builder headers(Header... h) {
            Arrays.stream(h).forEach(header -> headers.put(header.getName(), header.getValues()));

            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
