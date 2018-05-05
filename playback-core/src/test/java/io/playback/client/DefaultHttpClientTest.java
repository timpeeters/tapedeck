package io.playback.client;

import io.playback.Request;
import io.playback.RequestMethod;
import io.playback.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultHttpClientTest {
    @RegisterExtension
    static HttpServerExtension server = HttpServerExtension.builder().build();

    @Test
    public void test() throws Exception {
        Response response = new DefaultHttpClient().execute(Request.builder()
                .method(RequestMethod.GET)
                .uri(new URL(server.getBaseUrl(), "/").toURI())
                .build());

        assertThat(response.getStatusCode()).isEqualTo("200");
    }
}
