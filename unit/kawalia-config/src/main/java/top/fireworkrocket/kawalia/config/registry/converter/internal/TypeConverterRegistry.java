package top.fireworkrocket.kawalia.config.registry.converter.internal;

import top.fireworkrocket.kawalia.config.registry.converter.api.TypeConverter;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TypeConverterRegistry implements top.fireworkrocket.kawalia.config.registry.converter.api.TypeConverterRegistry {

    private final Map<Type, TypeConverter<?>> converters = new ConcurrentHashMap<>();

    public TypeConverterRegistry() {
        registerDefaultConverters();
    }

    private void registerDefaultConverters() {
        register(String.class, s -> s);
        register(Integer.class, Integer::parseInt);
        register(int.class, Integer::parseInt);
        register(Long.class, Long::parseLong);
        register(long.class, Long::parseLong);
        register(Boolean.class, Boolean::parseBoolean);
        register(boolean.class, Boolean::parseBoolean);
        register(Double.class, Double::parseDouble);
        register(double.class, Double::parseDouble);
        register(Float.class, Float::parseFloat);
        register(float.class, Float::parseFloat);
        register(UUID.class, UUID::fromString);
    }

    @Override
    public <T> void register(Type type, TypeConverter<T> converter) {
        converters.put(type, converter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> convert(String value, Type targetType) {
        TypeConverter<?> converter = converters.get(targetType);
        if (converter == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable((T) converter.convert(value));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}