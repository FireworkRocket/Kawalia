package top.fireworkrocket.kawalia.network.discovery.assembly.service.api;

import reactor.core.publisher.Flux;
import top.fireworkrocket.kawalia.network.discovery.assembly.DiscoveryBuilder;
import top.fireworkrocket.kawalia.network.discovery.packet.device.DeviceProperties;

import java.io.IOException;

public interface DiscoveryServiece {
    Flux<DeviceProperties> start(DiscoveryBuilder builder) throws IOException;
    void stop() throws IOException;

}
