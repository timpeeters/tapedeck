package io.tapedeck;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface HashCodeContractTester<T> {
    T getInstance();

    @Test
    default void hashCodeConsistency() {
        assertThat(getInstance().hashCode()).isEqualTo(getInstance().hashCode()).as("hashCode should be consistent");
    }
}
