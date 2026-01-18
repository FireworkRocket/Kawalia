package top.fireworkrocket.kawalia.exception.common;

import top.fireworkrocket.kawalia.exception.BaseTranslatableException;

import java.io.IOException;

public class UncheckedI18nIOException extends BaseTranslatableException {

    public UncheckedI18nIOException(String i18nKey, Object... args) {
        super(i18nKey, args);
    }

    public UncheckedI18nIOException(IOException cause, String i18nKey) {
        super(cause, i18nKey);
    }

    public UncheckedI18nIOException(IOException cause, String i18nKey, Object... args) {
        super(cause, i18nKey, args);
    }

    @Override
    public synchronized IOException getCause() {
        return (IOException) super.getCause();
    }
}