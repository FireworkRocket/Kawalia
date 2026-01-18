package top.fireworkrocket.kawalia.config.exception;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class ConfigurationException extends BaseTranslatableException {

    public ConfigurationException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public ConfigurationException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public ConfigurationException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}
