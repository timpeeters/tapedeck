package io.tapedeck.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {
    @Test
    public void isEmpty_null() {
        assertTrue(StringUtils.isEmpty(null));
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
