package io.playback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Header {
    private final String name;
    private final List<String> values;

    private Header(Builder builder) {
        this.name = builder.name;
        this.values = Collections.unmodifiableList(builder.values);
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private String name;
        private final List<String> values = new ArrayList<>();

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withValue(String value) {
            values.add(value);

            return this;
        }

        public Builder withValues(String... values) {
            this.values.addAll(Arrays.asList(values));

            return this;
        }

        public Header build() {
            return new Header(this);
        }
    }
}
