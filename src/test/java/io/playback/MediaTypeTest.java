package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MediaTypeTest implements EqualsContractTester<MediaType>, HashCodeContractTester<MediaType> {

    @Test
    public void testToString() {
        assertThat(new MediaType("text", "plain").toString()).isEqualTo("text/plain");
    }

    @Override
    public MediaType getInstance() {
        return MediaType.parse("application/json; charset=utf-8");
    }
}
