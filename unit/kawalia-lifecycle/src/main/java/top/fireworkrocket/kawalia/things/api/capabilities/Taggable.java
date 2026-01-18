package top.fireworkrocket.kawalia.things.api.capabilities;

import java.util.Map;

@FunctionalInterface
public interface Taggable {
    Map<String, String> getTags();

    default String getTag(String key) {
        return getTags().get(key);
    }
}
    