package top.fireworkrocket.kawalia.grooving.api.session.capabilities;

import java.util.UUID;

@FunctionalInterface
public interface Identifiable {
    UUID getId();
}
