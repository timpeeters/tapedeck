package io.playback.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {
    @MethodSource("isEmptyValues")
    @ParameterizedTest(name = "{0}")
    void isEmpty(ArgumentsAccessor args) {
        assertThat(StringUtils.isEmpty(args.getString(1)))
                .as(args.getString(0))
                .isEqualTo(args.getBoolean(2));
    }

    static Stream<Arguments> isEmptyValues() {
        return Stream.of(
                Arguments.of("null should return true", null, true),
                Arguments.of("empty string should return true", "", true),
                Arguments.of("non empty string should return false", "a", false));
    }
}
