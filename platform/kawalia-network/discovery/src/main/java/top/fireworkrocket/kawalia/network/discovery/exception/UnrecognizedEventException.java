package top.fireworkrocket.kawalia.network.discovery.exception;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class UnrecognizedEventException extends BaseTranslatableException {
    public UnrecognizedEventException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public UnrecognizedEventException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public UnrecognizedEventException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}
