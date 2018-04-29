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
        MockHttpServletRequest req = getRoot();

        assertThat(RequestMapper.map(req)).satisfies(r -> {
            assertThat(r.getMethod()).isEqualTo(RequestMethod.GET); assertThat(r.getUri()).isEqualTo(URI.create("/")); int a = 1;
        });
    }

    @Test
    public void map_nullQueryParam() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString(null);

        assertThat(RequestMapper.map(req).getQueryParams()).isEmpty();
    }

    @Test
    public void map_emptyQueryParam() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString("");

        assertThat(RequestMapper.map(req).getQueryParams()).isEmpty();
    }

    @Test
    public void map_oneQueryParam() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString("id=1");

        assertThat(RequestMapper.map(req).getQueryParams())
                .containsExactly(new SimpleEntry<>("id", new String[] {"1"}));
    }

    @Test
    public void map_oneQueryParam_multipleValues() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString("id=1&id=2");

        assertThat(RequestMapper.map(req).getQueryParams())
                .containsExactly(new SimpleEntry<>("id", new String[] {"1", "2"}));
    }

    @Test
    public void map_multipleQueryParams() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString("q=test&page=1");

        assertThat(RequestMapper.map(req).getQueryParams())
                .containsExactly(
                        new SimpleEntry<>("q", new String[] {"test"}),
                        new SimpleEntry<>("page", new String[] {"1"}));
    }

    @Test
    public void map_queryParamContainingUrlEncodedCharacters() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString("dir%20name=%2Fhome");

        assertThat(RequestMapper.map(req).getQueryParams())
                .containsExactly(new SimpleEntry<>("dir name", new String[] {"/home"}));
    }

    @Test
    public void map_queryParamWithEmptyValue() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString("q=");

        assertThat(RequestMapper.map(req).getQueryParams())
                .containsExactly(new SimpleEntry<>("q", new String[] {""}));
    }

    @Test
    public void map_queryParamWithoutEqualSign() {
        MockHttpServletRequest req = getRoot();
        req.setQueryString("a&b&c");

        assertThat(RequestMapper.map(req).getQueryParams())
                .containsExactly(
                        new SimpleEntry<>("a", new String[] {""}),
                        new SimpleEntry<>("b", new String[] {""}),
                        new SimpleEntry<>("c", new String[] {""}));
    }

    private MockHttpServletRequest getRoot() {
        return new MockHttpServletRequest("GET", "/");
    }
}
