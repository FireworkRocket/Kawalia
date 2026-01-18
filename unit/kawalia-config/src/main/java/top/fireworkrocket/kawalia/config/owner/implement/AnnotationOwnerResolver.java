package top.fireworkrocket.kawalia.config.owner.implement;

import top.fireworkrocket.kawalia.config.definition.Configuration;
import top.fireworkrocket.kawalia.config.owner.api.OwnerResolver;

import java.util.Optional;

public class AnnotationOwnerResolver implements OwnerResolver {

    @Override
    public Optional<String> resolve(Class<?> ownerClass) {
        Configuration configAnnotation = ownerClass.getAnnotation(Configuration.class);
        if (configAnnotation == null) {
            return Optional.empty();
        }

        if (!configAnnotation.prefix().isEmpty()) {
            return Optional.of(configAnnotation.prefix());
        }

        return Optional.of(ownerClass.getSimpleName().toLowerCase());
    }
}
    