package io.playback.servlet;

import org.springframework.mock.web.MockHttpServletRequest;

public class MockHttpServletRequestBuilder {
    private final MockHttpServletRequest request;

    public MockHttpServletRequestBuilder(String method, String requestUri) {
        this.request = new MockHttpServletRequest(method, requestUri);
    }

    public MockHttpServletRequest build() {
        return request;
    }

    public MockHttpServletRequestBuilder queryString(String queryString) {
        request.setQueryString(queryString);

        return this;
    }

    public static MockHttpServletRequestBuilder get(String uri) {
        return new MockHttpServletRequestBuilder("GET", uri);
    }
}
