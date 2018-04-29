package io.playback.servlet;

import io.playback.Header;
import io.playback.Headers;
import io.playback.QueryParam;
import io.playback.RequestMethod;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {
    @Test
    public void map() {
        assertThat(RequestMapper.map(MockHttpServletRequestBuilder.get("/")
                .queryString("q=test")
                .header(Headers.ACCEPT, "application/json")
                .build()))
                .satisfies(r -> {
                    assertThat(r.getMethod()).isEqualTo(RequestMethod.GET);
                    assertThat(r.getUri()).isEqualTo(URI.create("/"));
                    assertThat(r.getQueryParams()).containsValue(QueryParam.queryParam("q", "test"));
                    assertThat(r.getHeaders()).containsValue(Header.builder()
                            .withName(Headers.ACCEPT)
                            .withValue("application/json")
                            .build());
                });
    }
}
