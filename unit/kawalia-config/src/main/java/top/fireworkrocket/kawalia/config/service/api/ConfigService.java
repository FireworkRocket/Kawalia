package top.fireworkrocket.kawalia.config.service.api;

public interface ConfigService {

    void initialize(Class<?>... initialClasses);

    void initialize(String... basePackages);

    void save();

    void inject(Object instance);
}