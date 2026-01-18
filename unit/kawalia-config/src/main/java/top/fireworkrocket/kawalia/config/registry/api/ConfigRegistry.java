package top.fireworkrocket.kawalia.config.registry.api;

import top.fireworkrocket.kawalia.config.registry.metadata.ConfigurationDefinition;
import top.fireworkrocket.kawalia.config.storage.record.Entry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface ConfigRegistry {

    void register(ConfigurationDefinition definition);

    void setValue(String key, Object value);

    <T> Optional<T> getValue(String key);

    Stream<Entry> stream();

    Stream<Entry> stream(String ownerId);

    Set<String> getAllOwnerIds();
}