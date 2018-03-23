package io.tapedeck;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface HashCodeContractTester<T> {
    T getInstance();

    /**
     * HashCode method should be consistent: x.hashCode() == x.hashCode().
     */
    @Test
    default void hashCodeConsistency() {
        T x = getInstance();

        assertThat(x.hashCode()).isEqualTo(x.hashCode())
                .as("hashCode method should be consistent");
    }

    /**
     * HashCode method should return equal value for equal objects: x.equals(y) -> x.hashCode() == y.hashCode().
     */
    @Test
    default void hashCodeEquality() {
        T x = getInstance();
        T y = getInstance();

        assertThat(x.hashCode()).isEqualTo(y.hashCode())
                .as("hashCode method should return equal value for equal objects");
    }
}
