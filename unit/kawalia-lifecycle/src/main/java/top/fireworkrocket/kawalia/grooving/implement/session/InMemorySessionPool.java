package top.fireworkrocket.kawalia.grooving.implement.session;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import top.fireworkrocket.kawalia.grooving.api.session.node.SessionNode;
import top.fireworkrocket.kawalia.grooving.api.session.pool.SessionPool;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemorySessionPool implements SessionPool {
    private final ConcurrentMap<UUID, SessionNode> pool = new ConcurrentHashMap<>();

    private final Sinks.Many<SessionNode> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public Flux<SessionNode> nodes() {
        return this.sink.asFlux();
    }

    @Override
    public Mono<Void> emit(Flux<SessionNode> nodeStream) {
        return nodeStream
                .doOnNext(node -> {
                    this.pool.put(node.getId(), node);
                    this.sink.tryEmitNext(node);
                })
                .then();
    }
}