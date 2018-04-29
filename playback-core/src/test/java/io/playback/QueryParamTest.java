package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryParamTest implements EqualsContractTester<QueryParam>, HashCodeContractTester<QueryParam> {
    @Override
    public QueryParam getInstance() {
        return QueryParam.queryParam("q", "playback.io");
    }

    @Test
    public void toStringTest() {
        assertThat(getInstance().toString()).isEqualTo("q=playback.io");
    }
}
