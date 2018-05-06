package io.playback.marshaller;

import io.playback.Response;

class YamlResponse {
    private String statusCode;
    private String statusText;

    YamlResponse() {
        this(null, null);
    }

    YamlResponse(String statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    static YamlResponse from(Response response) {
        return new YamlResponse(response.getStatusCode(), response.getStatusText());
    }

    Response unmarshal() {
        return Response.builder()
                .statusCode(statusCode)
                .statusText(statusText)
                .build();
    }
}
