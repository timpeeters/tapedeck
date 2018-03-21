package io.tapedeck;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface HashCodeContractTester<T> {
    T getInstance();

    /**
     * Whenever hashCode is invoked on the same object more than once during an execution of a Java application, the
     * hashCode method must consistently return the same value.
     */
    @Test
    default void hashCodeConsistency() {
        T a = getInstance();

        assertThat(a.hashCode()).isEqualTo(a.hashCode()).as("hashCode should be consistent");
    }

    @Test
    default void hashCodeEquality() {
        T a = getInstance();
        T b = getInstance();

        assertThat(a.hashCode()).isEqualTo(b.hashCode()).as("hashCode should be equal");
    }
}
