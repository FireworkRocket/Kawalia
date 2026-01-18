package top.fireworkrocket.kawalia.network.discovery.strategy.api.converter;

import top.fireworkrocket.kawalia.network.discovery.packet.device.DeviceProperties;
import top.fireworkrocket.kawalia.network.discovery.strategy.event.discovery.DiscoveryEvent;

import java.util.Optional;

@FunctionalInterface
public interface DiscoveryEventConverter {
    Optional<DeviceProperties> convert(DiscoveryEvent event);
}
