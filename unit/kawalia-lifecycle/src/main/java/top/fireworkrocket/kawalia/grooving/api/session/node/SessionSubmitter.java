package top.fireworkrocket.kawalia.grooving.api.session.node;

import reactor.core.publisher.Mono;

public interface SessionSubmitter {
    Mono<SessionNode> submit(SessionNode nodeToSubmit);
}