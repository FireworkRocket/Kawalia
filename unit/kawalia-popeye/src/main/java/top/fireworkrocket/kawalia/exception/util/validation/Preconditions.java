package top.fireworkrocket.kawalia.exception.util.validation;

import top.fireworkrocket.kawalia.exception.common.I18nIllegalArgumentException;
import top.fireworkrocket.kawalia.exception.common.I18nIllegalStateException;

public final class Preconditions {

    private Preconditions() {}

    public static void checkArgument(boolean expression, String messageKey, Object... args) {
        if (!expression) {
            throw new I18nIllegalArgumentException(messageKey, args);
        }
    }

    public static void checkState(boolean expression, String messageKey, Object... args) {
        if (!expression) {
            throw new I18nIllegalStateException(messageKey, args);
        }
    }
}