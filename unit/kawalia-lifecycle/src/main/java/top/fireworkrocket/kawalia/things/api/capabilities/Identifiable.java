package top.fireworkrocket.kawalia.things.api.capabilities;

import java.util.UUID;

@FunctionalInterface
public interface Identifiable {
    UUID getId();
}
