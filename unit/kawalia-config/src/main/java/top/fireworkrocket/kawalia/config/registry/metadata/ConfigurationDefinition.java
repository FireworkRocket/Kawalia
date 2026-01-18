package top.fireworkrocket.kawalia.config.registry.metadata;

import java.lang.reflect.Type;

public record ConfigurationDefinition(
        String key,
        String ownerId,
        Type type,
        Object defaultValue
) {}
