package top.fireworkrocket.kawalia.config.scanner.api;

import top.fireworkrocket.kawalia.config.registry.metadata.ConfigurationDefinition;

import java.util.Set;

public interface ConfigScanner {
    Set<ConfigurationDefinition> scan(Class<?>... classes);
}
