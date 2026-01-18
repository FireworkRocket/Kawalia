package top.fireworkrocket.kawalia.exception.api;

public interface ExceptionHandler {

    void logError(Throwable e, String key, Object... args);

    void logError(String key, Object... args);

    void logWarn(Throwable e, String key, Object... args);

    void logWarn(String key, Object... args);

    void logInfo(Throwable e, String key, Object... args);

    void logInfo(String key, Object... args);

    void logDebug(Throwable e, String key, Object... args);

    void logDebug(String key, Object... args);

    String handleException(Throwable e, String key, Object... args);

    void handleAndThrow(Throwable e, String key, Object... args);

    void logFatalAndExit(Throwable e, String messageKey, int exitCode, Runnable runnable, Object... args);
}
