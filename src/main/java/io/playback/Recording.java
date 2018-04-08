package io.playback;

public class Recording {
    private final Request request;
    private final Response response;

    public Recording(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public Request getRequest() {
        return request;
    }
}
