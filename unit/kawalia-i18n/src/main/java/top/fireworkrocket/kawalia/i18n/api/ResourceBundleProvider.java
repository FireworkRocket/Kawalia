package top.fireworkrocket.kawalia.i18n.api;

import java.util.Locale;
import java.util.ResourceBundle;

public interface ResourceBundleProvider {
    ResourceBundle getResourceBundle(Locale locale);
}
