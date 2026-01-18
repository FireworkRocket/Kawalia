package top.fireworkrocket.kawalia.network.discovery.strategy.event.discovery;

public record DiscoveryEvent(
        DeviceStatus status,
        Object data
) {}