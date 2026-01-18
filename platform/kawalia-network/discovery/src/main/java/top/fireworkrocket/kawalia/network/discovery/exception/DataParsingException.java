package top.fireworkrocket.kawalia.network.discovery.exception;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

public class DataParsingException extends BaseTranslatableException {
    public DataParsingException(Throwable cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }

    public DataParsingException(Throwable cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public DataParsingException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }
}
