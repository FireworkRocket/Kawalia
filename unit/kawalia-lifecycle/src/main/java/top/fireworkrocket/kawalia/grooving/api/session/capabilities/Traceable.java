package top.fireworkrocket.kawalia.grooving.api.session.capabilities;

import java.util.Optional;

public interface Traceable {
    Optional<String> getRoot();
    Optional<String> getParent();
}