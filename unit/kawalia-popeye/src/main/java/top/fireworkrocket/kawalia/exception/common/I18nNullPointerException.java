package top.fireworkrocket.kawalia.exception.common;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class I18nNullPointerException extends BaseTranslatableException {

    public I18nNullPointerException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public I18nNullPointerException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public I18nNullPointerException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}