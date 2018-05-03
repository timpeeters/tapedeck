package io.playback.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssertTest {
    @Test
    public void notEmpty() {
        assertThatCode(() -> Assert.notEmpty("abc", "")).doesNotThrowAnyException();
    }

    @Test
    public void notEmpty_null() {
        assertThatThrownBy(() -> Assert.notEmpty(null, "argument must not be null"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("argument must not be null");
    }

    @Test
    public void notEmpty_empty() {
        assertThatThrownBy(() -> Assert.notEmpty("", "argument must not be empty"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("argument must not be empty");
    }

    @Test
    public void notNull() {
        assertThatCode(() -> Assert.notNull(new Object())).doesNotThrowAnyException();
    }

    @Test
    public void notNull_null() {
        assertThatThrownBy(() -> Assert.notNull(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("must not be null");
    }
}
