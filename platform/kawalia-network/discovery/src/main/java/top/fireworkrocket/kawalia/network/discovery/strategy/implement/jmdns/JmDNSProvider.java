package top.fireworkrocket.kawalia.network.discovery.strategy.implement.jmdns;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import top.fireworkrocket.kawalia.exception.util.validation.Assertions;
import top.fireworkrocket.kawalia.network.discovery.strategy.api.strategy.DiscoveryStrategy;
import top.fireworkrocket.kawalia.network.discovery.strategy.event.discovery.DeviceStatus;
import top.fireworkrocket.kawalia.network.discovery.strategy.event.discovery.DiscoveryEvent;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;

public class JmDNSProvider implements DiscoveryStrategy {

    private final Sinks.Many<DiscoveryEvent> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final AtomicReference<JmDNS> jmdnsInstance = new AtomicReference<>(null);
    private final String serviceType;
    private ServiceListener listener;

    public JmDNSProvider(String serviceType) {
        this.serviceType = Assertions.requireNonNull(
                serviceType,
                "network.discovery.strategy.serviceType"
        );
    }

    @Override
    public Flux<DiscoveryEvent> start(InetAddress address) throws IOException {
        if (jmdnsInstance.compareAndSet(null, JmDNS.create(address))) {
            JmDNS jmdns = jmdnsInstance.get();
            this.listener = new ServiceListener() {
                @Override
                public void serviceAdded(ServiceEvent event) {
                    jmdns.requestServiceInfo(event.getType(), event.getName());
                }

                @Override
                public void serviceResolved(ServiceEvent event) {
                    sink.tryEmitNext(new DiscoveryEvent(DeviceStatus.FOUND, event.getInfo()));
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {
                    sink.tryEmitNext(new DiscoveryEvent(DeviceStatus.LOST, event.getInfo()));
                }

            };

            jmdns.addServiceListener(this.serviceType, this.listener);
        }

        return sink.asFlux();
    }

    @Override
    public void stop() throws IOException {
        JmDNS jmDNS = jmdnsInstance.getAndSet(null);
        if (jmDNS != null) {
            jmDNS.close();
            jmDNS.removeServiceListener(this.serviceType, this.listener);
            sink.tryEmitComplete();
        }
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }
}
