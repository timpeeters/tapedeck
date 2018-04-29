package io.playback;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class QueryParam {
    private final String name;
    private final List<String> values;

    private QueryParam(String name, List<String> values) {
        this.name = name;
        this.values = Collections.unmodifiableList(values);
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof QueryParam)) {
            return false;
        }

        QueryParam otherQueryParam = (QueryParam) otherObject;

        return Objects.equals(name, otherQueryParam.name) && Objects.equals(values, otherQueryParam.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }

    @Override
    public String toString() {
        return name + "=" + values.stream().collect(Collectors.joining(", "));
    }

    public static QueryParam queryParam(String name, String... values) {
        return new QueryParam(name, Arrays.asList(values));
    }

    public static QueryParam empty(String name) {
        return new QueryParam(name, Collections.emptyList());
    }
}
