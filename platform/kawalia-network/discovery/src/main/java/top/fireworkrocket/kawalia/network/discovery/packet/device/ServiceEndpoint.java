package top.fireworkrocket.kawalia.network.discovery.packet.device;

import org.jspecify.annotations.NonNull;
import top.fireworkrocket.kawalia.exception.util.validation.Assertions;

import java.util.Collections;
import java.util.Map;

public record ServiceEndpoint(
        @NonNull String serviceName,
        @NonNull String protocol,
        int port,
        Map<String, String> metadata
) {
    public ServiceEndpoint {
        Assertions.requireNonNull(serviceName, "network.discovery.endpoint.name");
        Assertions.requireNonNull(protocol, "network.discovery.endpoint.protocol");
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("network.discovery.error.endpoint.port_invalid");
        }
        metadata = Collections.unmodifiableMap(new java.util.HashMap<>(metadata));
    }
}
