package top.fireworkrocket.kawalia.i18n.api;

import java.util.Locale;

public interface Messages {

    String get(String key);

    String get(String key, Object... args);

    String get(Locale locale, String key, Object... args);

    String get(ResourceBundleProvider provider, Locale locale, String key, Object... args);
}
