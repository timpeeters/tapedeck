package io.playback.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PlaybackServletTest {
    @Test
    public void service() throws Exception {
        MockHttpServletRequest req = MockHttpServletRequestBuilder.get("/").build();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        new PlaybackServlet().service(req, resp);

        assertThat(resp.getStatus()).isEqualTo(HttpServletResponse.SC_NO_CONTENT);
    }
}
