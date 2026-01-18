package top.fireworkrocket.kawalia.config.registry.converter.api;

@FunctionalInterface
public interface TypeConverter<T> {
    T convert(String value) throws Exception;
}