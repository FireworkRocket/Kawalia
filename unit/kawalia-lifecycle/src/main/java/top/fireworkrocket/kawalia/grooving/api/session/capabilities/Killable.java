package top.fireworkrocket.kawalia.grooving.api.session.capabilities;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface Killable {
    Mono<Void> kill();
}
