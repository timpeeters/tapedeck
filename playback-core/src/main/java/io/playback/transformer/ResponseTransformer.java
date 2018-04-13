package io.playback.transformer;

import io.playback.Response;

@FunctionalInterface
public interface ResponseTransformer {
    Response transform(Response response);
}
