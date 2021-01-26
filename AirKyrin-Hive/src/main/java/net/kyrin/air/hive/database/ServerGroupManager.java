package net.kyrin.air.hive.database;

import com.rethinkdb.net.Result;
import net.kyrin.air.hive.database.container.DatabasePattern;
import net.kyrin.air.hive.database.models.ServerGroup;

import java.util.ArrayList;
import java.util.List;

public class ServerGroupManager extends DatabasePattern<ServerGroup> {

    public ServerGroupManager(String table) {
        super(table);
    }

    @Override
    public boolean isExist(String key) {
        return get(key) != null;
    }

    @Override
    public void insert(ServerGroup serverGroup) {
        if (!isExist(serverGroup.getName())) {
            getTable().insert(serverGroup).run(getConnection());
        }
    }

    @Override
    public void delete(String key) {
        getTable().filter(row -> row.g("name").eq(key)).delete().run(getConnection());

    }

    @Override
    public ServerGroup get(String key) {
        Result<ServerGroup> run = getTable().filter(row -> row.g("name").eq(key)).run(getConnection(), ServerGroup.class);
        if (run.hasNext()) {
            return run.next();
        } else {
            return null;
        }
    }

    @Override
    public List<ServerGroup> getAll() {
        List<ServerGroup> serverGroups = new ArrayList<>();
        Result<ServerGroup> run = getTable().run(getConnection(), ServerGroup.class);
        while (run.hasNext()) {
            serverGroups.add(run.next());
        }
        return serverGroups;
    }
}
