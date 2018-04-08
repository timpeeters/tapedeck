package io.playback.transformer;

import io.playback.Response;

public abstract class AbstractResponseBodyTransformer implements ResponseTransformer {
    @Override
    public Response transform(Response response) {
        return Response.builder()
                .like(response)
                .body(transformBody(response))
                .build();
    }

    public abstract String transformBody(Response response);
}
