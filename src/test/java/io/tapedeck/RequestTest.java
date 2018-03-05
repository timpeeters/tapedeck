package io.tapedeck;

import org.junit.Test;

public class RequestTest {
    @Test(expected = IllegalArgumentException.class)
    public void get_invalidUri() {
        Request.get(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void post_invalidUri() {
        Request.post(" ");
    }
}
