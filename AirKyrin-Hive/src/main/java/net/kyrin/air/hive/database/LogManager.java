package net.kyrin.air.hive.database;

import com.rethinkdb.net.Result;
import net.kyrin.air.hive.database.container.DatabasePattern;
import net.kyrin.air.hive.database.models.LogEntry;
import net.kyrin.air.hive.database.models.ServerGroup;

import java.util.List;

public class LogManager extends DatabasePattern<LogEntry> {

    public LogManager() {
        super("log");
    }

    @Override
    public boolean isExist(String key) {
        return get(key) != null;
    }

    @Override
    public void insert(LogEntry logEntry) {
        getTable().insert(logEntry).run(getConnection());
    }

    @Override
    public void delete(String serverUUID) {
        getTable().filter(row -> row.g("serverUUID").eq(serverUUID)).delete().run(getConnection(), LogEntry.class);
    }

    @Override
    public LogEntry get(String serverUUID) {
        Result<LogEntry> run = getTable().filter(row -> row.g("serverUUID").eq(serverUUID)).run(getConnection(), LogEntry.class);
        if (run.hasNext()) {
            return run.next();
        } else {
            return null;
        }
    }

    @Override
    public List<LogEntry> getAll() {
        return getTable().run(getConnection(), LogEntry.class).toList();
    }
}
