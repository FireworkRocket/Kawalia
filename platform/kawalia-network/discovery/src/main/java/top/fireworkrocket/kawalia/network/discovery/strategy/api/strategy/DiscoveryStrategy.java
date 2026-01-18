package top.fireworkrocket.kawalia.network.discovery.strategy.api.strategy;

import reactor.core.publisher.Flux;
import top.fireworkrocket.kawalia.network.discovery.strategy.event.discovery.DiscoveryEvent;

import java.io.IOException;
import java.net.InetAddress;

public interface DiscoveryStrategy {

    Flux<DiscoveryEvent> start(InetAddress address) throws IOException;

    void stop() throws IOException;

    String getName();
}