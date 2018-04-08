package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface EqualsContractTester<T> {
    T getInstance();

    /**
     * Equals method should be reflexive: x.equals(x) = true.
     */
    @Test
    default void equalsIsReflexive() {
        T x = getInstance();

        assertThat(x).isEqualTo(x)
                .as("equals method should be reflexive");
    }

    /**
     * Equals method should be symmetric: x.equals(y) = y.equals(x).
     */
    @Test
    default void equalsIsSymmetric() {
        T x = getInstance();
        T y = getInstance();

        assertThat(x.equals(y) && y.equals(x)).isTrue()
                .as("equals method should be symmetric");
    }

    /**
     * Equals method should be transitive: x.equals(y) = y.equals(z) = true -> x.equals(z) = true.
     */
    @Test
    default void equalsIsTransitive() {
        T x = getInstance();
        T y = getInstance();
        T z = getInstance();

        assertThat(x.equals(y) && y.equals(z) && x.equals(z)).isTrue()
                .as("equals method should be transitive");
    }

    /**
     * Equals method should return false when comparing to null.
     */
    @Test
    default void equalsAgainstNull() {
        T x = getInstance();

        assertThat(x).isNotEqualTo(null)
                .as("equals method should return false when comparing to null");
    }
}
