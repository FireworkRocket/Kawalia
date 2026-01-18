package top.fireworkrocket.kawalia.config.registry.converter.api;

import java.lang.reflect.Type;
import java.util.Optional;

public interface TypeConverterRegistry {
    <T> void register(Type type, TypeConverter<T> converter);
    <T> Optional<T> convert(String value, Type targetType);
}