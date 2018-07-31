package io.playback.matcher;

import io.playback.EqualsContractTester;
import io.playback.HashCodeContractTester;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultTest implements EqualsContractTester<Result>, HashCodeContractTester<Result> {

    @Test
    public void testToString() {
        assertThat(getInstance().toString()).isEqualTo("Result[distance=0.0]");
    }

    @Override
    public Result getInstance() {
        return Result.exactMatch();
    }
}
