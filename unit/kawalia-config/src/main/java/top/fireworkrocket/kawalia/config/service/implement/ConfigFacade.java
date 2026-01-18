package top.fireworkrocket.kawalia.config.service.implement;


import top.fireworkrocket.kawalia.config.exception.ConfigScanException;
import top.fireworkrocket.kawalia.config.registry.api.ConfigRegistry;
import top.fireworkrocket.kawalia.config.scanner.api.ConfigScanner;
import top.fireworkrocket.kawalia.config.service.api.ConfigService;
import top.fireworkrocket.kawalia.config.storage.api.ConfigStorage;
import top.fireworkrocket.kawalia.config.storage.record.Entry;
import top.fireworkrocket.kawalia.config.util.ClassFinder;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConfigFacade implements ConfigService {

    private final ConfigScanner scanner;
    private final ConfigRegistry registry;
    private final ConfigStorage storage;
    private final AnnotationConfigInjector injector;

    public ConfigFacade(
            ConfigScanner scanner,
            ConfigRegistry registry,
            ConfigStorage storage,
            AnnotationConfigInjector injector
    ) {
        this.scanner = scanner;
        this.registry = registry;
        this.storage = storage;
        this.injector = injector;
    }

    @Override
    public void initialize(Class<?>... initialClasses) {
        scanner.scan(initialClasses).forEach(registry::register);

        storage.streamAll().forEach(entry ->
                registry.setValue(
                        entry.ownerId() + "." + entry.key(),
                        entry.value()
                )
        );
    }

    @Override
    public void initialize(String... basePackages) {
        try {
            Set<Class<?>> foundClasses = ClassFinder.findClasses(basePackages);
            initialize(foundClasses.toArray(new Class<?>[0]));
        } catch (Exception e) {
            throw new ConfigScanException(e, "config.error.scan.package.failed", (Object[]) basePackages);
        }
    }

    @Override
    public void save() {
        for (String ownerId : registry.getAllOwnerIds()) {
            Map<String, String> valuesToSave = registry.stream(ownerId)
                    .filter(entry -> entry.value() != null)
                    .collect(
                            Collectors.toMap(
                                    entry -> extractSimpleKey(entry.key(), ownerId),
                                    Entry::value
                    ));
            storage.saveAll(ownerId, valuesToSave);
        }
    }

    @Override
    public void inject(Object instance) {
        injector.inject(instance);
    }

    private String extractSimpleKey(String fullKey, String ownerId) {
        String prefix = ownerId + ".";
        return fullKey.startsWith(prefix) ? fullKey.substring(prefix.length()) : fullKey;
    }
}