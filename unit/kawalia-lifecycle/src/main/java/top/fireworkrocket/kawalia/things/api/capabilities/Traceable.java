package top.fireworkrocket.kawalia.things.api.capabilities;

import java.util.Optional;

public interface Traceable {
    Optional<String> getRoot();
    Optional<String> getParent();
}