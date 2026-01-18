package top.fireworkrocket.kawalia.exception.common;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class I18nUnsupportedOperationException extends BaseTranslatableException {

    public I18nUnsupportedOperationException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public I18nUnsupportedOperationException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public I18nUnsupportedOperationException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}