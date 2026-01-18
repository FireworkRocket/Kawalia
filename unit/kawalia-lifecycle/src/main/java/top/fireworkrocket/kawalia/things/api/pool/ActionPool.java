package top.fireworkrocket.kawalia.things.api.pool;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.fireworkrocket.kawalia.things.api.node.ActionNode;

public interface ActionPool {

    Mono<Void> emit(Flux<? extends ActionNode> actionStream);

    Flux<ActionNode> flux();
}