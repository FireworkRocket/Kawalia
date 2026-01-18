package top.fireworkrocket.kawalia.config.owner.api;

import java.util.Optional;

@FunctionalInterface
public interface OwnerResolver {
    Optional<String> resolve(Class<?> ownerClass);
}
    