package io.playback.client;

import io.playback.Header;
import io.playback.Headers;
import io.playback.Request;
import io.playback.RequestMethod;
import io.playback.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultHttpClientTest {
    @RegisterExtension
    static HttpServerExtension server = HttpServerExtension.builder().build();

    @Test
    public void execute() throws Exception {
        Response response = new DefaultHttpClient().execute(Request.builder()
                .method(RequestMethod.GET)
                .uri(new URL(server.getBaseUrl(), "/").toURI())
                .header(() -> Header.header(Headers.ACCEPT, "text/html"))
                .build());

        assertThat(response.getStatusCode()).isEqualTo("200");
    }

    @Test
    public void execute_malformedUrl() {
        assertThatThrownBy(() -> new DefaultHttpClient().execute(Request.builder().uri(URI.create("x://test")).build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Malformed URL");
    }

    @Test
    public void execute_emulateIoException() {
        assertThatThrownBy(() -> new DefaultHttpClient().execute(Request.builder()
                .method(RequestMethod.GET)
                .uri(URI.create("http://127.0.0.1:0/"))
                .build())).isInstanceOf(UncheckedIOException.class);
    }
}
