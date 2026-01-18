package top.fireworkrocket.kawalia.config.storage.strategy;

import org.jooq.DSLContext;
import top.fireworkrocket.kawalia.config.storage.api.ConfigStorage;
import top.fireworkrocket.kawalia.config.storage.record.Entry;

import java.util.Optional;
import java.util.stream.Stream;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class JooqConfigStorage implements ConfigStorage {

    private static final String TABLE_NAME = "nexus_config";
    private static final org.jooq.Table<?> T = table(TABLE_NAME);
    private static final org.jooq.Field<String> F_OWNER_ID = field("owner_id", String.class);
    private static final org.jooq.Field<String> F_CONFIG_KEY = field("config_key", String.class);
    private static final org.jooq.Field<String> F_CONFIG_VALUE = field("config_value", String.class);

    private final DSLContext ctx;

    public JooqConfigStorage(DSLContext dslContext) {
        this.ctx = dslContext;
        initSchema();
    }

    private void initSchema() {
        ctx.createTableIfNotExists(T)
                .column(F_OWNER_ID)
                .column(F_CONFIG_KEY)
                .column(F_CONFIG_VALUE)
                .primaryKey(F_OWNER_ID, F_CONFIG_KEY)
                .execute();
    }

    @Override
    public void save(String ownerId, String key, String value) {
        ctx.insertInto(T, F_OWNER_ID, F_CONFIG_KEY, F_CONFIG_VALUE)
                .values(ownerId, key, value)
                .onDuplicateKeyUpdate()
                .set(F_CONFIG_VALUE, value)
                .execute();
    }

    @Override
    public Optional<String> getValue(String ownerId, String key) {
        return ctx.select(F_CONFIG_VALUE)
                .from(T)
                .where(F_OWNER_ID.eq(ownerId).and(F_CONFIG_KEY.eq(key)))
                .fetchOptional(F_CONFIG_VALUE);
    }

    @Override
    public void remove(String ownerId, String key) {
        ctx.deleteFrom(T)
                .where(F_OWNER_ID.eq(ownerId).and(F_CONFIG_KEY.eq(key)))
                .execute();
    }

    @Override
    public Stream<Entry> stream(String ownerId) {
        return ctx.select(F_CONFIG_KEY, F_CONFIG_VALUE)
                .from(T)
                .where(F_OWNER_ID.eq(ownerId))
                .fetchStream()
                .map(rec -> new Entry(ownerId, rec.value1(), rec.value2()));
    }

    @Override
    public Stream<Entry> streamAll() {
        return ctx.select(F_OWNER_ID, F_CONFIG_KEY, F_CONFIG_VALUE)
                .from(T)
                .fetchStream()
                .map(rec -> new Entry(rec.value1(), rec.value2(), rec.value3()));
    }
}
