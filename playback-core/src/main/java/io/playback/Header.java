package io.playback;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Header {
    private final String name;
    private final List<String> values;

    private Header(String name, List<String> values) {
        this.name = name;
        this.values = Collections.unmodifiableList(values);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof Header)) {
            return false;
        }

        Header otherHeader = (Header) otherObject;

        return Objects.equals(name, otherHeader.name) && Objects.equals(values, otherHeader.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }

    @Override
    public String toString() {
        return name + "=" + values.stream().collect(Collectors.joining(", "));
    }

    public static Header header(String name, String... values) {
        return new Header(name, Arrays.asList(values));
    }
}
