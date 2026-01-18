package top.fireworkrocket.kawalia.exception.util.validation;

import top.fireworkrocket.kawalia.exception.common.I18nIllegalArgumentException;

import java.util.Collection;
import java.util.Map;

public final class Assertions {

    private Assertions() {}


    private static final String KEY_REQUIRED_GENERIC = "validation.required.generic";
    private static final String KEY_REQUIRED_CONTEXTUAL = "validation.required.contextual";

    public static <T> T requireNonNull(T obj) {
        if (obj == null) {
            throw new I18nIllegalArgumentException(KEY_REQUIRED_GENERIC);
        }
        return obj;
    }

    public static <T> T requireNonNull(T obj, String context) {
        if (obj == null) {
            throw new I18nIllegalArgumentException(KEY_REQUIRED_CONTEXTUAL, context);
        }
        return obj;
    }

    public static String requireNonBlank(String str, String messageKey) {
        if (str == null || str.trim().isEmpty()) {
            throw new I18nIllegalArgumentException(messageKey);
        }
        return str;
    }

    public static <T extends Collection<?>> T requireNonEmpty(T collection, String messageKey) {
        if (collection == null || collection.isEmpty()) {
            throw new I18nIllegalArgumentException(messageKey);
        }
        return collection;
    }

    public static <T extends Map<?, ?>> T requireNonEmpty(T map, String messageKey) {
        if (map == null || map.isEmpty()) {
            throw new I18nIllegalArgumentException(messageKey);
        }
        return map;
    }
}