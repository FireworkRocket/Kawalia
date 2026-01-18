package top.fireworkrocket.kawalia.config.exception;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class ConfigInjectionException extends BaseTranslatableException {

    public ConfigInjectionException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public ConfigInjectionException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }
}