package io.tapedeck;

public class Recording {
    private final Request request;
    private final Response response;

    public Recording(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Response getResponse() {
        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;

        return response;
    }

    public Request getRequest() {
        return request;
    }
}
