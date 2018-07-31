package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderTest implements EqualsContractTester<Header>, HashCodeContractTester<Header> {
    @Test
    void toStringTest() {
        assertThat(getInstance().toString()).isEqualTo("Accept=text/html");
    }

    @Override
    public Header getInstance() {
        return Header.header(Headers.ACCEPT, "text/html");
    }
}
