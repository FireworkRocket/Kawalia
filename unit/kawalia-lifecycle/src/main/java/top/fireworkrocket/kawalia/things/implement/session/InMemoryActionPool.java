package top.fireworkrocket.kawalia.things.implement.session;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import top.fireworkrocket.kawalia.things.api.node.ActionNode;
import top.fireworkrocket.kawalia.things.api.pool.ActionPool;

public class InMemoryActionPool implements ActionPool {

    private final Sinks.Many<ActionNode> channel;

    public InMemoryActionPool() {
        this.channel = Sinks.many().multicast().onBackpressureBuffer();
    }

    @Override
    public Mono<Void> emit(Flux<? extends ActionNode> nodeStream) {
        return nodeStream
                .doOnNext(this.channel::tryEmitNext)
                .then();
    }

    @Override
    public Flux<ActionNode> flux() {
        return this.channel.asFlux();
    }
}