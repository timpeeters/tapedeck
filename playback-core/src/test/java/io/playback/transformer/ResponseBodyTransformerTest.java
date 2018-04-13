package io.playback.transformer;

import io.playback.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyTransformerTest {
    @Test
    public void test() {
        Response originalResponse = Response.builder().body("original").build();

        assertThat(new AbstractResponseBodyTransformer() {
            @Override
            public String transformBody(Response response) {
                return "replaced";
            }
        }.transform(originalResponse).getBodyAsString()).isEqualTo("replaced");
    }
}
