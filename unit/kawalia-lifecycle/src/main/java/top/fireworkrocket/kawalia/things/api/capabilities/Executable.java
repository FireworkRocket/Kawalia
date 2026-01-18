package top.fireworkrocket.kawalia.things.api.capabilities;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface Executable<R> {
    Mono<R> run();
}