package io.playback.marshaller;

import io.playback.Response;

public class YamlResponse {
    public String statusCode;
    public String statusText;

    public YamlResponse() {
        this(null, null);
    }

    public YamlResponse(String statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public static YamlResponse from(Response response) {
        return new YamlResponse(response.getStatusCode(), response.getStatusText());
    }

    public Response unmarshal() {
        return Response.builder()
                .statusCode(statusCode)
                .statusText(statusText)
                .build();
    }
}
