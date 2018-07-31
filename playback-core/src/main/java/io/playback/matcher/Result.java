package io.playback.matcher;

import java.util.StringJoiner;

public final class Result {
    private final boolean exactMatch;

    private Result(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public boolean isExactMatch() {
        return exactMatch;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("exactMatch=" + isExactMatch())
                .toString();
    }

    public static Result exactMatch() {
        return new Result(true);
    }

    public static Result noMatch() {
        return new Result(false);
    }

    public static Result of(boolean exactMatch) {
        return new Result(exactMatch);
    }
}
