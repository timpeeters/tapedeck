package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MediaTypeTest implements EqualsContractTester<MediaType>, HashCodeContractTester<MediaType> {
    @Test
    void testToString() {
        assertThat(MediaType.of("text", "plain").toString()).isEqualTo("text/plain");
    }

    @Override
    public MediaType getInstance() {
        return MediaType.parse("application/json; charset=utf-8");
    }
}
