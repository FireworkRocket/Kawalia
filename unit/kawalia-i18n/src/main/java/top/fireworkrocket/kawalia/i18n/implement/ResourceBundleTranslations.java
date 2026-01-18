package top.fireworkrocket.kawalia.i18n.implement;

import top.fireworkrocket.kawalia.i18n.api.Messages;
import top.fireworkrocket.kawalia.i18n.api.ResourceBundleProvider;
import top.fireworkrocket.kawalia.i18n.api.Translations;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleTranslations implements Translations {

    private final ResourceBundleProvider mainProvider;

    public ResourceBundleTranslations(ResourceBundleProvider mainProvider) {
        this.mainProvider = mainProvider;
    }

    @Override
    public Messages forLocale(Locale locale) {
        return new ResourceBundleMessages(locale, this.mainProvider, this::getInternal);
    }

    private String getInternal(ResourceBundleProvider provider, String key, Locale locale, Object... args) {
        try {
            ResourceBundle bundle = provider.getResourceBundle(locale);
            String message = bundle.getString(key);
            if (args == null || args.length == 0) {
                return message;
            }
            return MessageFormat.format(message, args);
        } catch (Exception e) {
            return key;
        }
    }
}
