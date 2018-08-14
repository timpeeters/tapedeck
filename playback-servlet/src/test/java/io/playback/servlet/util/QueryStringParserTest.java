package io.playback.servlet.util;

import io.playback.QueryParam;
import org.junit.jupiter.api.Test;

import static io.playback.servlet.util.QueryStringParser.parse;
import static org.assertj.core.api.Assertions.assertThat;

class QueryStringParserTest {
    @Test
    void map_nullQueryParam() {
        assertThat(parse(null)).isEmpty();
    }

    @Test
    void map_emptyQueryParam() {
        assertThat(parse("")).isEmpty();
    }

    @Test
    void map_oneQueryParam() {
        assertThat(parse("id=1")).containsExactly(QueryParam.queryParam("id", "1"));
    }

    @Test
    void map_oneQueryParam_multipleValues() {
        assertThat(parse("id=1&id=2")).containsExactly(QueryParam.queryParam("id", "1", "2"));
    }

    @Test
    void map_multipleQueryParams() {
        assertThat(parse("q=test&page=1")).containsExactly(
                QueryParam.queryParam("q", "test"),
                QueryParam.queryParam("page", "1"));
    }

    @Test
    void map_queryParamContainingUrlEncodedCharacters() {
        assertThat(parse("dir%20name=%2Fhome")).containsExactly(QueryParam.queryParam("dir name", "/home"));
    }

    @Test
    void map_queryParamWithEmptyValue() {
        assertThat(parse("q=")).containsExactly(QueryParam.empty("q"));
    }

    @Test
    void map_queryParamWithoutEqualSign() {
        assertThat(parse("a&b&c")).containsExactly(
                QueryParam.empty("a"),
                QueryParam.empty("b"),
                QueryParam.empty("c"));
    }
}
