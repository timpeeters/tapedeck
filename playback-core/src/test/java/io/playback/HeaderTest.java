package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderTest implements EqualsContractTester<Header>, HashCodeContractTester<Header> {
    @Override
    public Header getInstance() {
        return Header.header(Headers.ACCEPT, "text/html");
    }

    @Test
    public void toStringTest() {
        assertThat(getInstance().toString()).isEqualTo("Accept=text/html");
    }
}
