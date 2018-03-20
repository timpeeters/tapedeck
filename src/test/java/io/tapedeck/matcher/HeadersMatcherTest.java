package io.tapedeck.matcher;

import io.tapedeck.Headers;
import io.tapedeck.Request;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadersMatcherTest {
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_XML = "application/xml";

    private HeadersMatcher matcher;

    @Before
    public void initialize() {
        matcher = new HeadersMatcher();
    }

    @Test
    public void matches_withoutHeaders() {
        assertThat(matcher.matches(Request.get().build(), Request.get().build())).isTrue();
    }

    @Test
    public void matches_sameHeader() {
        assertThat(matcher.matches(
                Request.get().header(Headers.ACCEPT, APPLICATION_JSON).build(),
                Request.get().header(Headers.ACCEPT, APPLICATION_JSON).build())).isTrue();
    }

    @Test
    public void matches_sameHeader_multipleValues() {
        assertThat(matcher.matches(Request.get().header(b -> b
                        .withName(Headers.ACCEPT)
                        .withValues(APPLICATION_XML, APPLICATION_JSON)).build(),
                Request.get().header(b -> b
                        .withName(Headers.ACCEPT)
                        .withValues(APPLICATION_XML, APPLICATION_JSON)).build())).isTrue();
    }

    @Test
    public void matches_differentValues() {
        assertThat(matcher.matches(Request.get().header(b -> b
                        .withName(Headers.ACCEPT)
                        .withValues("text/html", "text/*")).build(),
                Request.get().header(b -> b
                        .withName(Headers.ACCEPT)
                        .withValues(APPLICATION_XML, APPLICATION_JSON)).build())).isFalse();
    }

    @Test
    public void matches_differentHeaderValue() {
        assertThat(matcher.matches(
                Request.get().header(Headers.ACCEPT, APPLICATION_JSON).build(),
                Request.get().header(Headers.ACCEPT, APPLICATION_XML).build())).isFalse();
    }
}
