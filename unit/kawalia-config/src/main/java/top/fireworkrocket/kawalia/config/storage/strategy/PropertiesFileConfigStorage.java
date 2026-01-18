package top.fireworkrocket.kawalia.config.storage.strategy;

import top.fireworkrocket.kawalia.config.exception.ConfigStorageException;
import top.fireworkrocket.kawalia.config.storage.api.ConfigStorage;
import top.fireworkrocket.kawalia.config.storage.record.Entry;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class PropertiesFileConfigStorage implements ConfigStorage {

    private static final String FILE_EXTENSION = ".properties";
    private final Path storageDirectory;

    public PropertiesFileConfigStorage(Path storageDirectory) {
        this.storageDirectory = storageDirectory;
        try {
            Files.createDirectories(storageDirectory);
        } catch (IOException e) {
            throw new ConfigStorageException(e, "config.error.storage.dir.create.failed", storageDirectory);
        }
    }

    @Override
    public void save(String ownerId, String key, String value) {
        modifyProperties(ownerId, props -> props.setProperty(key, value));
    }

    @Override
    public Optional<String> getValue(String ownerId, String key) {
        Properties props = loadProperties(ownerId);
        return Optional.ofNullable(props.getProperty(key));
    }

    @Override
    public void remove(String ownerId, String key) {
        modifyProperties(ownerId, props -> props.remove(key));
    }

    @Override
    public Stream<Entry> stream(String ownerId) {
        Properties props = loadProperties(ownerId);
        return props.stringPropertyNames().stream()
                .map(key -> new Entry(ownerId, key, props.getProperty(key)));
    }

    @Override
    public Stream<Entry> streamAll() {
        try {
            return Files.list(storageDirectory)
                    .filter(path -> path.toString().endsWith(FILE_EXTENSION))
                    .map(this::getOwnerIdFromFile)
                    .flatMap(this::stream);
        } catch (IOException e) {
            throw new ConfigStorageException(e, "config.error.storage.load.all.failed", storageDirectory);
        }
    }

    private Properties loadProperties(String ownerId) {
        Path filePath = getFilePath(ownerId);
        Properties props = new Properties();
        if (!Files.exists(filePath)) {
            return props;
        }
        try (Reader reader = Files.newBufferedReader(filePath)) {
            props.load(reader);
        } catch (IOException e) {
            throw new ConfigStorageException(e, "config.error.storage.file.load.failed", filePath);
        }
        return props;
    }

    private void modifyProperties(String ownerId, java.util.function.Consumer<Properties> action) {
        Path filePath = getFilePath(ownerId);
        Properties props = loadProperties(ownerId);
        action.accept(props);
        try (Writer writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            props.store(writer, ownerId);
        } catch (IOException e) {
            throw new ConfigStorageException(e, "config.error.storage.file.save.failed", filePath);
        }
    }

    private Path getFilePath(String ownerId) {
        return storageDirectory.resolve(ownerId + FILE_EXTENSION);
    }

    private String getOwnerIdFromFile(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.length() - FILE_EXTENSION.length());
    }
}