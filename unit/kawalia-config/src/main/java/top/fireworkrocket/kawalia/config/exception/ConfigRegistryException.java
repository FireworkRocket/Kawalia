package top.fireworkrocket.kawalia.config.exception;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class ConfigRegistryException extends BaseTranslatableException {
    public ConfigRegistryException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }
}