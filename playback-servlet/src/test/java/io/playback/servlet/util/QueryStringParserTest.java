package io.playback.servlet.util;

import io.playback.QueryParam;
import org.junit.jupiter.api.Test;

import static io.playback.servlet.util.QueryStringParser.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringParserTest {
    @Test
    public void map_nullQueryParam() {
        assertThat(parse(null)).isEmpty();
    }

    @Test
    public void map_emptyQueryParam() {
        assertThat(parse("")).isEmpty();
    }

    @Test
    public void map_oneQueryParam() {
        assertThat(parse("id=1")).containsExactly(QueryParam.queryParam("id", "1"));
    }

    @Test
    public void map_oneQueryParam_multipleValues() {
        assertThat(parse("id=1&id=2")).containsExactly(QueryParam.queryParam("id", "1", "2"));
    }

    @Test
    public void map_multipleQueryParams() {
        assertThat(parse("q=test&page=1")).containsExactly(
                QueryParam.queryParam("q", "test"),
                QueryParam.queryParam("page", "1"));
    }

    @Test
    public void map_queryParamContainingUrlEncodedCharacters() {
        assertThat(parse("dir%20name=%2Fhome")).containsExactly(QueryParam.queryParam("dir name", "/home"));
    }

    @Test
    public void map_queryParamWithEmptyValue() {
        assertThat(parse("q=")).containsExactly(QueryParam.empty("q"));
    }

    @Test
    public void map_queryParamWithoutEqualSign() {
        assertThat(parse("a&b&c")).containsExactly(
                QueryParam.empty("a"),
                QueryParam.empty("b"),
                QueryParam.empty("c"));
    }
}
