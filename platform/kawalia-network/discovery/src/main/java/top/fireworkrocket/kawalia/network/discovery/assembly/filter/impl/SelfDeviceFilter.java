package top.fireworkrocket.kawalia.network.discovery.assembly.filter.impl;

import top.fireworkrocket.kawalia.network.discovery.assembly.filter.api.DiscoveryFilter;
import top.fireworkrocket.kawalia.network.discovery.strategy.event.discovery.DiscoveryEvent;

public class SelfDeviceFilter implements DiscoveryFilter {

    private final DiscoveryEvent selfDevice;

    public SelfDeviceFilter(DiscoveryEvent device) {
        this.selfDevice = device;
    }

    @Override
    public boolean doFilter(DiscoveryEvent device) {
        return !selfDevice.equals(device);
    }
}
