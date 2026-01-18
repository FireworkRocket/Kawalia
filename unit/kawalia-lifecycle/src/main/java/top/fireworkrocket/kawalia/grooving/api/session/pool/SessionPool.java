package top.fireworkrocket.kawalia.grooving.api.session.pool;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.fireworkrocket.kawalia.grooving.api.session.node.SessionNode;

public interface SessionPool {

    Flux<SessionNode> nodes();

    Mono<Void> emit(Flux<SessionNode> nodeStream);

}