package top.fireworkrocket.kawalia.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public abstract class BaseTranslatableException extends RuntimeException {

    private final String i18nKey;
    private final Object[] args;

    public BaseTranslatableException(String i18nKey, Object... args) {
        super(i18nKey);
        this.i18nKey = i18nKey;
        this.args = args;
    }

    public BaseTranslatableException(Throwable cause, String i18nKey) {
        this(cause, i18nKey, (Object[]) null);
    }

    public BaseTranslatableException(Throwable cause, String i18nKey, Object... args) {
        super(i18nKey, cause);
        this.i18nKey = i18nKey;
        this.args = args;
    }

    @Override
    public String getMessage() {
        return i18nKey;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = "i18nKey='" + i18nKey + "'";
        if (args != null && args.length > 0) {
            message += ", args=" + Arrays.toString(args);
        }
        return s + ": " + message;
    }
}