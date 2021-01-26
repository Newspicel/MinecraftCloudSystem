package net.kyrin.air.hive.database.container;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.Db;
import com.rethinkdb.gen.ast.Table;
import com.rethinkdb.net.Connection;
import lombok.Getter;

@Getter
public class DatabaseManager {

    private static String databaseName = "airkyrin";

    private RethinkDB rethinkDB;
    private Connection connection;
    private Db database;

    public DatabaseManager(RethinkDB rethinkDB, Connection connection) {
        this.rethinkDB = rethinkDB;
        this.connection = connection;
        this.connection.use(databaseName);
        this.database = this.rethinkDB.db(databaseName);
        createDataBaseIfNotExist();
    }

    void createTableIfNotExist(String table) {
        if (!(boolean) rethinkDB.db(databaseName).tableList().contains(table).run(connection).next()) {
            rethinkDB.db(databaseName).tableCreate(table).run(connection);
        }
    }

    private void createDataBaseIfNotExist() {
        if (!(boolean) rethinkDB.dbList().contains(databaseName).run(connection).next()) {
            rethinkDB.dbCreate(databaseName).run(connection);
        }
    }

    Table getTable(String table) {
        return rethinkDB.db(databaseName).table(table);
    }
}
