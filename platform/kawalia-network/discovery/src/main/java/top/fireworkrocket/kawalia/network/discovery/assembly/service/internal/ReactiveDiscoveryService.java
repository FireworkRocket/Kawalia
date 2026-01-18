package top.fireworkrocket.kawalia.network.discovery.assembly.service.internal;

import reactor.core.publisher.Flux;
import top.fireworkrocket.kawalia.network.discovery.assembly.DiscoveryBuilder;
import top.fireworkrocket.kawalia.network.discovery.strategy.api.converter.DiscoveryEventConverter;
import top.fireworkrocket.kawalia.network.discovery.assembly.filter.api.DiscoveryFilter;
import top.fireworkrocket.kawalia.network.discovery.assembly.service.api.DiscoveryServiece;
import top.fireworkrocket.kawalia.network.discovery.packet.device.DeviceProperties;
import top.fireworkrocket.kawalia.network.discovery.strategy.api.strategy.DiscoveryStrategy;

import java.io.IOException;
import java.util.Optional;

public class ReactiveDiscoveryService implements DiscoveryServiece {

    private DiscoveryStrategy strategy;

    @Override
    public Flux<DeviceProperties> start(DiscoveryBuilder builder) throws IOException {
        DiscoveryFilter filter = builder.getFilter();
        this.strategy = builder.getStrategy();
        DiscoveryEventConverter converter = builder.getConverter();
        return strategy.start(builder.getAddress())
                .filter(filter::doFilter)
                .map(converter::convert)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }


    @Override
    public void stop() throws IOException {
        if (this.strategy != null) {
            this.strategy.stop();
        }
    }
}
