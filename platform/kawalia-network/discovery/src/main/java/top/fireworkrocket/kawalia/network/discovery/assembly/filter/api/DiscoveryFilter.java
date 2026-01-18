package top.fireworkrocket.kawalia.network.discovery.assembly.filter.api;


import top.fireworkrocket.kawalia.network.discovery.strategy.event.discovery.DiscoveryEvent;

@FunctionalInterface
public interface DiscoveryFilter {
    boolean doFilter(DiscoveryEvent device);
}


