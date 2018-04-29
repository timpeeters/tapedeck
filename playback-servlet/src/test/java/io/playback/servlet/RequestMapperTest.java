package io.playback.servlet;

import io.playback.QueryParam;
import io.playback.RequestMethod;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {
    @Test
    public void map() {
        MockHttpServletRequest req = MockHttpServletRequestBuilder.get("/").queryString("q=test").build();

        assertThat(RequestMapper.map(req)).satisfies(r -> {
            assertThat(r.getMethod()).isEqualTo(RequestMethod.GET);
            assertThat(r.getUri()).isEqualTo(URI.create("/"));
            assertThat(r.getQueryParams()).containsValue(QueryParam.queryParam("q", "test"));
        });
    }
}
