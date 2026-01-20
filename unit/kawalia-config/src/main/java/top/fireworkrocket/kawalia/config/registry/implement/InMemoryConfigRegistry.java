package top.fireworkrocket.kawalia.config.registry.implement;

import top.fireworkrocket.kawalia.config.registry.converter.api.TypeConverterRegistry;
import top.fireworkrocket.kawalia.config.exception.ConfigRegistryException;
import top.fireworkrocket.kawalia.config.registry.api.ConfigRegistry;
import top.fireworkrocket.kawalia.config.registry.metadata.ConfigurationDefinition;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class InMemoryConfigRegistry implements ConfigRegistry {

    private final Map<String, ConfigurationDefinition> definitions = new ConcurrentHashMap<>();
    private final Map<String, Object> values = new ConcurrentHashMap<>();
    private final TypeConverterRegistry converterRegistry;

    public InMemoryConfigRegistry(TypeConverterRegistry converterRegistry) {
        if (converterRegistry == null) {
            throw new ConfigRegistryException("config.error.registry.converter.null");
        }
        this.converterRegistry = converterRegistry;
    }

    @Override
    public void register(ConfigurationDefinition definition) {
        definitions.put(definition.key(), definition);
        if (definition.defaultValue() != null) {
            values.put(definition.key(), definition.defaultValue());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getValue(String key) {
        return Optional.ofNullable((T) values.get(key));
    }

    @Override
    public void setValue(String key, Object value) {
        ConfigurationDefinition definition = definitions.get(key);
        if (definition == null) {
            return;
        }

        Object finalValue = processValue(key, definition.type(), value);
        values.put(key, finalValue);
    }

    private Object processValue(String key, Type expectedType, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return converterRegistry.convert(value.toString(), expectedType)
                    .orElseThrow(() -> new ConfigRegistryException(
                            "config.error.registry.conversion.failed",
                            value, expectedType.getTypeName(), key
                    ));
        }

        if (!isTypeCompatible(expectedType, value.getClass())) {
            throw new ConfigRegistryException(
                    "config.error.registry.type.mismatch",
                    key, expectedType.getTypeName(), value.getClass().getName()
            );
        }
        return value;
    }

    @Override
    public Stream<Entry> stream() {
        return values.entrySet().stream()
                .map(entry -> new Entry(entry.getKey(), entry.getValue()));
    }

    @Override
    public Stream<Entry> stream(String ownerId) {
        return definitions.values().stream()
                .filter(def -> ownerId.equals(def.ownerId()))
                .map(def -> new Entry(def.key(), values.get(def.key())));
    }

    @Override
    public Set<String> getAllOwnerIds() {
        return definitions.values().stream()
                .map(ConfigurationDefinition::ownerId)
                .collect(Collectors.toSet());
    }

    private boolean isTypeCompatible(Type expectedType, Class<?> actualClass) {
        if (expectedType instanceof Class<?> expectedClass) {
            if (expectedClass.isPrimitive()) {
                return getWrapperClass(expectedClass).isAssignableFrom(actualClass);
            }
            return expectedClass.isAssignableFrom(actualClass);
        }
        if (expectedType instanceof ParameterizedType) {
            return isTypeCompatible(((ParameterizedType) expectedType).getRawType(), actualClass);
        }
        return false;
    }

    private Class<?> getWrapperClass(Class<?> primitiveClass) {
        if (primitiveClass == int.class) return Integer.class;
        if (primitiveClass == long.class) return Long.class;
        if (primitiveClass == double.class) return Double.class;
        if (primitiveClass == boolean.class) return Boolean.class;
        if (primitiveClass == float.class) return Float.class;
        if (primitiveClass == char.class) return Character.class;
        if (primitiveClass == byte.class) return Byte.class;
        if (primitiveClass == short.class) return Short.class;
        return primitiveClass;
    }
}