package io.playback.matcher;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public final class Result {
    private final double distance;

    private Result(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isExactMatch() {
        return distance == 0;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof Result)) {
            return false;
        }

        Result otherResult = (Result) otherObject;

        return Objects.equals(distance, otherResult.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("distance=" + distance)
                .toString();
    }

    public static Result aggregate(Result... results) {
        return new Result(Arrays.stream(results).mapToDouble(Result::getDistance).average().orElse(0));
    }

    public static Result exactMatch() {
        return new Result(0);
    }

    public static Result noMatch() {
        return new Result(1);
    }

    public static Result of(boolean exactMatch) {
        return exactMatch ? exactMatch() : noMatch();
    }
}
