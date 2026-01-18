package top.fireworkrocket.kawalia.config.exception;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class ConfigStorageException extends BaseTranslatableException {

    public ConfigStorageException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public ConfigStorageException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public ConfigStorageException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}
