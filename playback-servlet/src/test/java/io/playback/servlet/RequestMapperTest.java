package io.playback.servlet;

import io.playback.RequestMethod;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {
    @Test
    public void map() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", "/");

        assertThat(RequestMapper.map(mockRequest)).satisfies(r -> {
            assertThat(r.getMethod()).isEqualTo(RequestMethod.GET);
            assertThat(r.getUri()).isEqualTo(URI.create("/"));
        });
    }
}
