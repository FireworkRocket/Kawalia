package top.fireworkrocket.kawalia.config.storage.api;

import top.fireworkrocket.kawalia.config.storage.record.Entry;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface ConfigStorage {

    Optional<String> getValue(String ownerId, String key);

    void save(String ownerId, String key, String value);

    default void saveAll(String ownerId, Map<String, String> values) {
        if (values == null) return;
        values.forEach((key, value) -> save(ownerId, key, value));
    }

    void remove(String ownerId, String key);

    Stream<Entry> stream(String ownerId);

    Stream<Entry> streamAll();
}