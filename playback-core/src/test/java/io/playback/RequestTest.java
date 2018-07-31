package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestTest implements EqualsContractTester<Request>, HashCodeContractTester<Request> {
    @Test
    void get_invalidUri() {
        assertThatThrownBy(() -> Request.get(" ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void post_invalidUri() {
        assertThatThrownBy(() -> Request.post(" ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toStringTest() {
        assertThat(getInstance().toString())
                .isEqualTo("Request[method=GET, uri=/, queryParams=[id=1], headers=[Accept=text/html]]");
    }

    @Override
    public Request getInstance() {
        return Request.get("/").header(Headers.ACCEPT, "text/html").queryParam("id", "1").build();
    }
}
