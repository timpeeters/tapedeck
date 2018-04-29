package io.playback.servlet;

import io.playback.RequestMethod;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URI;
import java.util.AbstractMap.SimpleEntry;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {
    @Test
    public void map() {
        MockHttpServletRequest mockReq = getRoot();

        assertThat(RequestMapper.map(mockReq)).satisfies(r -> {
            assertThat(r.getMethod()).isEqualTo(RequestMethod.GET);
            assertThat(r.getUri()).isEqualTo(URI.create("/"));
        });
    }

    @Test
    public void map_nullQueryParam() {
        MockHttpServletRequest mockReq = getRoot();
        mockReq.setQueryString(null);

        assertThat(RequestMapper.map(mockReq).getQueryParams()).isEmpty();
    }

    @Test
    public void map_emptyQueryParam() {
        MockHttpServletRequest mockReq = getRoot();
        mockReq.setQueryString("");

        assertThat(RequestMapper.map(mockReq).getQueryParams()).isEmpty();
    }

    @Test
    public void map_oneQueryParam() {
        MockHttpServletRequest mockReq = getRoot();
        mockReq.setQueryString("id=1");

        assertThat(RequestMapper.map(mockReq).getQueryParams())
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(new SimpleEntry<>("id", new String[]{"1"}));
    }

    @Test
    public void map_oneQueryParam_multipleValues() {
        MockHttpServletRequest mockReq = getRoot();
        mockReq.setQueryString("id=1&id=2");

        assertThat(RequestMapper.map(mockReq).getQueryParams())
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(new SimpleEntry<>("id", new String[]{"1", "2"}));
    }

    @Test
    public void map_multipleQueryParams() {
        MockHttpServletRequest mockReq = getRoot();
        mockReq.setQueryString("q=test&page=1");

        assertThat(RequestMapper.map(mockReq).getQueryParams())
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(
                        new SimpleEntry<>("q", new String[]{"test"}),
                        new SimpleEntry<>("page", new String[]{"1"}));
    }

    @Test
    public void map_queryParamContainingUrlEncodedCharacters() {
        MockHttpServletRequest mockReq = getRoot();
        mockReq.setQueryString("dir%20name=%2Fhome");

        assertThat(RequestMapper.map(mockReq).getQueryParams())
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(new SimpleEntry<>("dir name", new String[] {"/home"}));
    }

    private MockHttpServletRequest getRoot() {
        return new MockHttpServletRequest("GET", "/");
    }
}
