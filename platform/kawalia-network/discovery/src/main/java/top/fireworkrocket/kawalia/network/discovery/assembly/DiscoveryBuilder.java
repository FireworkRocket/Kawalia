package top.fireworkrocket.kawalia.network.discovery.assembly;

import lombok.Getter;
import top.fireworkrocket.kawalia.exception.util.validation.Assertions;
import top.fireworkrocket.kawalia.network.discovery.assembly.filter.api.DiscoveryFilter;
import top.fireworkrocket.kawalia.network.discovery.strategy.api.converter.DiscoveryEventConverter;
import top.fireworkrocket.kawalia.network.discovery.strategy.api.strategy.DiscoveryStrategy;

import java.io.IOException;
import java.net.InetAddress;

public class DiscoveryBuilder {

    @Getter
    private DiscoveryStrategy strategy;
    @Getter
    private InetAddress address;
    @Getter
    private DiscoveryFilter filter;
    @Getter
    private DiscoveryEventConverter converter;

    public DiscoveryBuilder setAddress(InetAddress address) {
        this.address = address;
        return this;
    }

    public DiscoveryBuilder setFilter(DiscoveryFilter filter) {
        this.filter = filter;
        return this;
    }

    public DiscoveryBuilder setStrategy(DiscoveryStrategy strategy,
                                        DiscoveryEventConverter converter
    ) {
        this.converter = converter;
        this.strategy = strategy;
        return this;
    }

    public DiscoveryBuilder build() throws IOException {
        Assertions.requireNonNull(this.strategy, "network.discovery.builder.strategy");
        Assertions.requireNonNull(this.converter, "network.discovery.builder.converter");
        Assertions.requireNonNull(this.filter, "network.discovery.builder.filter");
        Assertions.requireNonNull(this.address, "network.discovery.builder.address");
        return this;
    }
}
