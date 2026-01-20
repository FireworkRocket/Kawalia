package top.fireworkrocket.kawalia.network.discovery.packet.device;

import top.fireworkrocket.kawalia.exception.util.validation.Assertions;

import java.util.*;

public record DeviceProperties(
        String uuid,
        String deviceName,
        String username,
        String osName,
        String version,
        List<ServiceEndpoint> endpoint,
        Map<String, String> additionalProperties
) {
    public DeviceProperties {
        Assertions.requireNonNull(
                uuid,
                "network.discovery.properties.uuid"
        );
        Assertions.requireNonNull(
                deviceName,
                "network.discovery.properties.deviceName"
        );
        Assertions.requireNonNull(
                username,
                "network.discovery.properties.username"
        );
        Assertions.requireNonNull(
                osName,
                "network.discovery.properties.osName"
        );
        Assertions.requireNonNull(
                version,
                "network.discovery.properties.version"
        );
        Assertions.requireNonNull(
                endpoint,
                "network.discovery.properties.endpointList"
        );
        Assertions.requireNonNull(
                additionalProperties,
                "network.discovery.properties.additional"
        );
        additionalProperties = Map.copyOf(additionalProperties);
    }

    public static DeviceProperties createDefault(
            String deviceName,
            String appVersion,
            List<ServiceEndpoint> endpoint
    ) {
        return new DeviceProperties(
                UUID.randomUUID().toString(),
                deviceName,
                System.getProperty("user.name"),
                System.getProperty("os.name"),
                appVersion,
                endpoint,
                Collections.emptyMap()
        );
    }
}
