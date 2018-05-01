package io.playback;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @MethodSource
    @ParameterizedTest(name = "{0}")
    void parse(ArgumentsAccessor args) {
        assertThat(MediaType.parse(args.getString(0))).isEqualTo(args.get(1, MediaType.class));
    }

    static Stream<Arguments> parse() {
        return Stream.of(
                Arguments.of("text/plain", MediaType.of("text", "plain")),
                Arguments.of("application/json", MediaType.of("application", "json")),
                Arguments.of("*; q=.2", MediaType.of("*", "*").param("q", ".2")),
                Arguments.of("text/plain/json;charset=UTF-8", MediaType.of("text", "plain").param("charset", "UTF-8")),
                Arguments.of("text/html; charset=iso-8859-1; q=0.7",
                        MediaType.of("text", "html").param("charset", "iso-8859-1").param("q", "0.7")));
    }
}
