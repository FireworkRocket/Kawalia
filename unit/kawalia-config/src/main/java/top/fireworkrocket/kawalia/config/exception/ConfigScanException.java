package top.fireworkrocket.kawalia.config.exception;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class ConfigScanException extends BaseTranslatableException {

    public ConfigScanException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public ConfigScanException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public ConfigScanException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}
