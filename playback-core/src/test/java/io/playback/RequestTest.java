package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestTest implements EqualsContractTester<Request>, HashCodeContractTester<Request> {
    @Override
    public Request getInstance() {
        return Request.get("/").header(Headers.ACCEPT, "text/html").queryParam("id", "1").build();
    }

    @Test
    public void get_invalidUri() {
        assertThatThrownBy(() -> Request.get(" ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void post_invalidUri() {
        assertThatThrownBy(() -> Request.post(" ")).isInstanceOf(IllegalArgumentException.class);
    }
}
