package io.playback.matcher;

import java.util.StringJoiner;

public final class Result {
    private final double distance;

    private Result(double distance) {
        this.distance = distance;
    }

    public boolean isExactMatch() {
        return distance == 0;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("distance=" + distance)
                .toString();
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
