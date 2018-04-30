package io.playback.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssertTest {
    @Test
    public void assertNotEmpty() {
        assertThatCode(() -> Assert.assertNotEmpty("abc", "")).doesNotThrowAnyException();
    }

    @Test
    public void assertNotEmpty_null() {
        assertThatThrownBy(() -> Assert.assertNotEmpty(null, "argument must not be null"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("argument must not be null");
    }

    @Test
    public void assertNotEmpty_empty() {
        assertThatThrownBy(() -> Assert.assertNotEmpty("", "argument must not be empty"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("argument must not be empty");
    }
}
