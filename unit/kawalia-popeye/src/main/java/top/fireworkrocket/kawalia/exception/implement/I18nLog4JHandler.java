package top.fireworkrocket.kawalia.exception.implement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import top.fireworkrocket.kawalia.exception.api.ExceptionHandler;
import top.fireworkrocket.kawalia.exception.api.HeapDump;
import top.fireworkrocket.kawalia.i18n.api.Messages;
import top.fireworkrocket.kawalia.i18n.api.Translations;

import java.util.Locale;

public final class I18nLog4JHandler implements ExceptionHandler {
    private static final String WRAPPER_FQCN = I18nLog4JHandler.class.getName();
    private static final Logger LOGGER = LogManager.getLogger(I18nLog4JHandler.class);

    private final Messages messages;
    private final HeapDump heapDumpService;

    public I18nLog4JHandler(Translations translations, Locale locale, HeapDump heapDumpService) {
        this.messages = translations.forLocale(locale);
        this.heapDumpService = heapDumpService;
    }

    private void log(Level level, String message, Throwable t) {
        if (LOGGER instanceof ExtendedLogger) {
            ((ExtendedLogger) LOGGER).logIfEnabled(WRAPPER_FQCN, level, null, message, t);
        } else {
            LOGGER.log(level, message, t);
        }
    }

    @Override
    public void logError(Throwable e, String key, Object... args) {
        log(Level.ERROR, messages.get(key, args), e);
    }

    @Override
    public void logError(String key, Object... args) {
        log(Level.ERROR, messages.get(key, args), null);
    }

    @Override
    public void logWarn(Throwable e, String key, Object... args) {
        log(Level.WARN, messages.get(key, args), e);
    }

    @Override
    public void logWarn(String key, Object... args) {
        log(Level.WARN, messages.get(key, args), null);
    }

    @Override
    public void logInfo(Throwable e, String key, Object... args) {
        log(Level.INFO, messages.get(key, args), e);
    }

    @Override
    public void logInfo(String key, Object... args) {
        log(Level.INFO, messages.get(key, args), null);
    }

    @Override
    public void logDebug(Throwable e, String key, Object... args) {
        log(Level.DEBUG, messages.get(key, args), e);
    }

    @Override
    public void logDebug(String key, Object... args) {
        log(Level.DEBUG, messages.get(key, args), null);
    }

    @Override
    public String handleException(Throwable e, String key, Object... args) {
        String localizedMessage = messages.get(key, args);
        log(Level.ERROR, localizedMessage, e);
        return String.format("%s: %s", localizedMessage, e.getMessage());
    }

    @Override
    public void handleAndThrow(Throwable e, String key, Object... args) {
        String localizedMessage = messages.get(key, args);
        log(Level.ERROR, localizedMessage, e);
        throw new RuntimeException(localizedMessage, e);
    }

    @Override
    public void logFatalAndExit(
            Throwable e,
            String messageKey,
            int exitCode,
            Runnable runnable,
            Object... args)
    {
        String localizedMessage = messages.get(messageKey, args);
        log(Level.FATAL, localizedMessage, e);
        heapDumpService.createHeapDump();
        runnable.run();
        System.exit(exitCode);
    }
}