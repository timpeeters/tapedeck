package io.playback;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MediaTypeParserTest {

    @Test
    public void parse_null() {
        assertThatThrownBy(() -> MediaType.parse(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void parse_emptyString() {
        assertThatThrownBy(() -> MediaType.parse("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void parse_textPlain() {
        assertThat(MediaType.parse("text/plain")).satisfies(mt -> {
            assertThat(mt.getType()).isEqualTo("text");
            assertThat(mt.getSubtype()).isEqualTo("plain");
        });
    }

    @Test
    public void parse_applicationJson() {
        assertThat(MediaType.parse("application/json")).satisfies(mt -> {
            assertThat(mt.getType()).isEqualTo("application");
            assertThat(mt.getSubtype()).isEqualTo("json");
        });
    }

    @Test
    public void parse_applicationJson_withEncoding() {
        assertThat(MediaType.parse("application/json;charset=UTF-8")).satisfies(mt -> {
            assertThat(mt.getType()).isEqualTo("application");
            assertThat(mt.getSubtype()).isEqualTo("json");
            assertThat(mt.getCharset()).isEqualTo(Charset.forName("UTF-8"));
        });
    }

    @Test
    public void parse_multipleParameters() {
        assertThat(MediaType.parse("text/html; charset=iso-8859-1; q=0.7")).satisfies(mt -> {
            assertThat(mt.getType()).isEqualTo("text");
            assertThat(mt.getSubtype()).isEqualTo("html");
            assertThat(mt.getCharset()).isEqualTo(Charset.forName("ISO-8859-1"));
        });
    }

    @Test
    public void parse_urlConnectionMediaType() {
        assertThat(MediaType.parse("*; q=.2")).satisfies(mt -> {
            assertThat(mt.getType()).isEqualTo("*");
            assertThat(mt.getSubtype()).isEqualTo("*");
        });
    }
}
