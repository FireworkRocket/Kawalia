package top.fireworkrocket.kawalia.config.service.implement;

import top.fireworkrocket.kawalia.config.definition.Configuration;
import top.fireworkrocket.kawalia.config.definition.InjectConfig;
import top.fireworkrocket.kawalia.config.exception.ConfigInjectionException;
import top.fireworkrocket.kawalia.config.owner.api.OwnerResolver;
import top.fireworkrocket.kawalia.config.registry.api.ConfigRegistry;

import java.lang.reflect.Field;

public class AnnotationConfigInjector {

    private final ConfigRegistry registry;
    private final OwnerResolver ownerResolver;

    public AnnotationConfigInjector(ConfigRegistry registry, OwnerResolver ownerResolver) {
        this.registry = registry;
        this.ownerResolver = ownerResolver;
    }

    public void inject(Object instance) {
        if (instance == null) {
            return;
        }
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectConfig.class)) {
                try {
                    injectField(instance, field);
                } catch (Exception e) {
                    throw new ConfigInjectionException(e, "config.error.injection.failed",
                            field.getName(), instance.getClass().getName());
                }
            }
        }
    }

    private void injectField(Object parentInstance, Field field) throws Exception {
        Class<?> configClass = field.getType();
        if (!configClass.isAnnotationPresent(Configuration.class)) {
            throw new ConfigInjectionException("config.error.injection.target.not_configurable",
                    configClass.getName(), field.getName());
        }

        Object configInstance = createAndPopulate(configClass);

        field.setAccessible(true);
        field.set(parentInstance, configInstance);
    }

    private Object createAndPopulate(Class<?> configClass) throws Exception {
        Object configInstance = configClass.getDeclaredConstructor().newInstance();
        String ownerId = ownerResolver.resolve(configClass)
                .orElseThrow(
                        () ->
                                new ConfigInjectionException(
                                        "config.error.injection.owner.unresolved",
                                        configClass.getName()
                                )
                );

        for (Field field : configClass.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String fullKey = ownerId + "." + field.getName();

            registry.getValue(fullKey).ifPresent(value -> {
                try {
                    field.setAccessible(true);
                    field.set(configInstance, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return configInstance;
    }
}