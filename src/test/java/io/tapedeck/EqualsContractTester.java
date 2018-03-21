package io.tapedeck;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface EqualsContractTester<T> {
    T getInstance();

    /**
     * For any non-null reference value a, a.equals(a) should return true.
     */
    @Test
    default void equalsIsReflexive() {
        T a = getInstance();

        assertThat(a.equals(a)).isTrue().as("equals method should be reflexive");
    }

    /**
     * For any non-null reference values a and b, a.equals(b) should return true if and only if b.equals(a) returns
     * true.
     */
    @Test
    default void equalsIsSymmetric() {
        T a = getInstance();
        T b = getInstance();

        assertThat(a.equals(b) && b.equals(a)).isTrue().as("equals method should be symmetric");
    }

    /**
     * For any non-null reference values a, b, and c, if a.equals(b) returns true and b.equals(c) returns true, then
     * a.equals(c) should return true.
     */
    @Test
    default void equalsIsTransitive() {
        T a = getInstance();
        T b = getInstance();
        T c = getInstance();

        assertThat(a.equals(b) && b.equals(c) && a.equals(c)).isTrue().as("equals method should be transitive");
    }

    /**
     * Tests whether equals holds up against null.
     */
    @Test
    default void equalsAgainstNull() {
        T a = getInstance();

        assertThat(a).isNotEqualTo(null).as("a should not be equal to null");
    }
}
