package top.fireworkrocket.kawalia.things.api.capabilities;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface Killable {
    Mono<Void> kill();
}
