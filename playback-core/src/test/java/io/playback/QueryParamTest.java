package io.playback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamTest implements EqualsContractTester<QueryParam>, HashCodeContractTester<QueryParam> {
    @Test
    void toStringTest() {
        assertThat(getInstance().toString()).isEqualTo("q=playback.io");
    }

    @Override
    public QueryParam getInstance() {
        return QueryParam.queryParam("q", "playback.io");
    }
}
