package top.fireworkrocket.kawalia.exception.common;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class I18nNoSuchElementException extends BaseTranslatableException {

    public I18nNoSuchElementException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public I18nNoSuchElementException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public I18nNoSuchElementException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}