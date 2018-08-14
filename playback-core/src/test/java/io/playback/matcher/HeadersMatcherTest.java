package io.playback.matcher;

import io.playback.Header;
import io.playback.Headers;
import io.playback.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeadersMatcherTest {
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_XML = "application/xml";

    private HeadersMatcher matcher;

    @BeforeEach
    void initialize() {
        matcher = new HeadersMatcher();
    }

    @Test
    void matches_withoutHeaders() {
        assertThat(matcher.matches(Request.get().build(), Request.get().build()).isExactMatch()).isTrue();
    }

    @Test
    void matches_sameHeader() {
        assertThat(matcher.matches(
                Request.get().header(Headers.ACCEPT, APPLICATION_JSON).build(),
                Request.get().header(Headers.ACCEPT, APPLICATION_JSON).build()).isExactMatch()).isTrue();
    }

    @Test
    void matches_sameHeader_multipleValues() {
        assertThat(matcher.matches(
                Request.get().header(() -> Header.header(Headers.ACCEPT, APPLICATION_XML, APPLICATION_JSON)).build(),
                Request.get().header(() -> Header.header(Headers.ACCEPT, APPLICATION_XML, APPLICATION_JSON)).build()
        ).isExactMatch()).isTrue();
    }

    @Test
    void matches_differentValues() {
        assertThat(matcher.matches(
                Request.get().header(() -> Header.header(Headers.ACCEPT, "text/html", "text/*")).build(),
                Request.get().header(() -> Header.header(Headers.ACCEPT, APPLICATION_XML, APPLICATION_JSON)).build()
        ).isExactMatch()).isFalse();
    }

    @Test
    void matches_differentHeaderValue() {
        assertThat(matcher.matches(
                Request.get().header(Headers.ACCEPT, APPLICATION_JSON).build(),
                Request.get().header(Headers.ACCEPT, APPLICATION_XML).build()).isExactMatch()).isFalse();
    }

    @Test
    void matches_multipleHeaderValuesOnlyOneMatches() {
        assertThat(matcher.matches(
                Request.get().header(Headers.ACCEPT, APPLICATION_JSON).build(),
                Request.get().header(() -> Header.header(Headers.ACCEPT, APPLICATION_JSON, APPLICATION_XML)).build()))
                .satisfies(r -> {
                    assertThat(r.isExactMatch()).isFalse();
                    assertThat(r.getDistance()).isEqualTo(0.5);
                });
    }
}
