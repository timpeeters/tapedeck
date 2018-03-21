package io.tapedeck.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
    @Test
    public void isEmpty_null() {
        assertThat(StringUtils.isEmpty(null)).isTrue();
    }

    @Test
    public void isEmpty_emptyString() {
        assertThat(StringUtils.isEmpty("")).isTrue();
    }

    @Test
    public void isEmpty_false() {
        assertThat(StringUtils.isEmpty("a")).isFalse();
    }
}
