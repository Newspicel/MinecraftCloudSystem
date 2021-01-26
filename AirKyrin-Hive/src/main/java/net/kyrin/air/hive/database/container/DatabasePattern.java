package net.kyrin.air.hive.database.container;

import com.rethinkdb.gen.ast.Table;
import com.rethinkdb.net.Connection;
import lombok.Getter;
import net.kyrin.air.hive.Hive;

import java.util.List;

@Getter
public abstract class DatabasePattern<T> {

    private DatabaseManager databaseManager;
    private Connection connection;
    private Table table;

    public DatabasePattern(String table) {
        this.databaseManager = Hive.getInstance().getDatabaseManager();
        this.connection = this.databaseManager.getConnection();
        this.table = databaseManager.getTable(table);
        this.databaseManager.createTableIfNotExist(table);
    }

    public abstract boolean isExist(String key);

    public abstract void insert(T t);

    public abstract void delete(String key);

    public abstract T get(String key);

    public abstract List<T> getAll();

}
