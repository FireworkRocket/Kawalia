package top.fireworkrocket.kawalia.exception.common;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class I18nIllegalArgumentException extends BaseTranslatableException {

    public I18nIllegalArgumentException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public I18nIllegalArgumentException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public I18nIllegalArgumentException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}
