package top.fireworkrocket.kawalia.config.scanner.implement;

import top.fireworkrocket.kawalia.config.definition.ConfigKey;
import top.fireworkrocket.kawalia.config.definition.Configuration;
import top.fireworkrocket.kawalia.config.exception.ConfigScanException;
import top.fireworkrocket.kawalia.config.registry.metadata.ConfigurationDefinition;
import top.fireworkrocket.kawalia.config.owner.api.OwnerResolver;
import top.fireworkrocket.kawalia.config.scanner.api.ConfigScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationConfigScanner implements ConfigScanner {

    private final OwnerResolver ownerResolver;

    public AnnotationConfigScanner(OwnerResolver ownerResolver) {
        if (ownerResolver == null) {
            throw new ConfigScanException("config.error.scanner.resolver.null");
        }
        this.ownerResolver = ownerResolver;
    }

    @Override
    public Set<ConfigurationDefinition> scan(Class<?>... classes) {
        if (classes == null) {
            return new HashSet<>();
        }

        return Arrays.stream(classes)
                .filter(Objects::nonNull)
                .filter(clazz -> clazz.isAnnotationPresent(Configuration.class))
                .flatMap(this::createDefinitionsForClass)
                .collect(Collectors.toSet());
    }

    private Stream<ConfigurationDefinition> createDefinitionsForClass(Class<?> clazz) {
        String ownerId = ownerResolver.resolve(clazz)
                .orElseThrow(() -> new ConfigScanException("config.error.scan.owner.unresolved", clazz.getName()));

        Object tempInstance = createTempInstance(clazz);

        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(field -> createDefinition(ownerId, field, tempInstance));
    }

    private ConfigurationDefinition createDefinition(String ownerId, Field field, Object tempInstance) {
        ConfigKey keyAnnotation = field.getAnnotation(ConfigKey.class);
        String simpleKey = (keyAnnotation != null && !keyAnnotation.value().isEmpty())
                ? keyAnnotation.value()
                : field.getName();

        String fullKey = ownerId + "." + simpleKey;
        Object defaultValue;

        try {
            field.setAccessible(true);
            defaultValue = field.get(tempInstance);
        } catch (IllegalAccessException e) {
            throw new ConfigScanException(e, "config.error.scan.field.inaccessible", field.getName(), field.getDeclaringClass().getName());
        } catch (Exception e) {
            throw new ConfigScanException(e, "config.error.scan.field.default.failed", field.getName(), field.getDeclaringClass().getName());
        }

        return new ConfigurationDefinition(fullKey, ownerId, field.getGenericType(), defaultValue);
    }

    private Object createTempInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ConfigScanException(e, "config.error.scan.instance.failed", clazz.getName());
        }
    }
}