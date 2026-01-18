package top.fireworkrocket.kawalia.i18n.implement;

import top.fireworkrocket.kawalia.i18n.api.Messages;
import top.fireworkrocket.kawalia.i18n.api.ResourceBundleProvider;

import java.util.Locale;

class ResourceBundleMessages implements Messages {

    private final Locale defaultLocale;
    private final ResourceBundleProvider mainProvider;
    private final TranslationEngine engine;

    @FunctionalInterface
    interface TranslationEngine {
        String translate(ResourceBundleProvider provider, String key, Locale locale, Object... args);
    }

    ResourceBundleMessages(
            Locale defaultLocale,
            ResourceBundleProvider mainProvider,
            TranslationEngine engine
    ) {
        this.defaultLocale = defaultLocale;
        this.mainProvider = mainProvider;
        this.engine = engine;
    }

    @Override
    public String get(String key) {
        return get(this.defaultLocale, key);
    }

    @Override
    public String get(String key, Object... args) {
        return get(this.defaultLocale, key, args);
    }

    @Override
    public String get(Locale locale, String key, Object... args) {
        return this.engine.translate(this.mainProvider, key, locale, args);
    }

    @Override
    public String get(ResourceBundleProvider provider, Locale locale, String key, Object... args) {
        return this.engine.translate(provider, key, locale, args);
    }
}