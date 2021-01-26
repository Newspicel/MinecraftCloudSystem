package net.kyrin.air.hive;


import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.Getter;
import net.kyrin.air.hive.bees.BeeManager;
import net.kyrin.air.hive.commands.CreateGroupCommand;
import net.kyrin.air.hive.commands.StartServer;
import net.kyrin.air.hive.commands.StopCommand;
import net.kyrin.air.hive.database.LogManager;
import net.kyrin.air.hive.database.ServerGroupManager;
import net.kyrin.air.hive.database.container.DatabaseManager;
import net.kyrin.air.hive.database.models.ServerGroup;
import net.kyrin.air.hive.file.FileManager;
import net.kyrin.air.hive.groups.GroupManager;
import net.kyrin.air.hive.hooks.ShutdownHook;
import net.kyrin.air.hive.process.ProcessManager;
import net.kyrin.air.hive.server.ServerManager;
import net.kyrin.air.hive.services.*;
import net.kyrin.air.hive.templates.TemplateManager;
import net.kyrin.air.lib.AirKyrinLibary;
import net.kyrin.air.lib.command.CloudCommandManager;
import net.kyrin.air.lib.command.CommandLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Getter
public class Hive {

    public static boolean RUNNING = false;
    private static Hive instance;

    private Server server;

    private AirKyrinLibary airKyrinLibary;
    private FileManager fileManager;
    private BeeManager beeManager;
    private GroupManager groupManager;
    private TemplateManager templateManager;
    private ProcessManager processManager;
    private ServerManager serverManager;

    private RethinkDB rethinkDB = RethinkDB.r;
    private DatabaseManager databaseManager;
    private ServerGroupManager serverGroupManager;
    private LogManager logManager;

    public Hive(String[] args) {
        instance = this;

        this.airKyrinLibary = new AirKyrinLibary("hive", "Hive#" + UUID.randomUUID().toString());
        this.fileManager = new FileManager();
        this.beeManager = new BeeManager();

        Connection connection = rethinkDB.connection().hostname("127.0.0.1").user(fileManager.getGlobalConfigManager().get().getDatabaseUser(), fileManager.getGlobalConfigManager().get().getDatabasePassword()).connect();

        this.databaseManager = new DatabaseManager(rethinkDB, connection);
        System.out.println(databaseManager.getConnection() == null ? "Rethinkdb connection failed!" : "Rethinkdb connection was successfully established!");
        this.serverGroupManager = new ServerGroupManager("ServerGroups");

        if (this.serverGroupManager.getAll().isEmpty()) {
            this.serverGroupManager.insert(new ServerGroup("proxy", "DYNAMIC", 1L, 100L, 0L, 512L, false, true));
            this.serverGroupManager.insert(new ServerGroup("lobby", "DYNAMIC", 1L, 30L, 0L, 512L, false, false));
        }

        new Thread(new CommandLoader()).start();
        this.logManager = new LogManager();
    }

    public void start() {
        RUNNING = true;
        this.templateManager = new TemplateManager();

        while (this.templateManager.createMinecraftServerResourcesFolder()) {
            System.out.println("Downloading");
        }

        this.groupManager = new GroupManager();
        System.out.println("Server groups in storage " + serverGroupManager.getAll().size());

        if (serverGroupManager.getAll().isEmpty()) {
            System.out.println("No server / proxy groups found!");
        } else {
            serverGroupManager.getAll().forEach(serverGroup ->
                    System.out.println("Imported group (" + serverGroup.getName() + ") with mode (" + serverGroup.getGroupMode() + ")"));
        }

        serverManager = new ServerManager();

        try {
            server = ServerBuilder.forPort(3333).maxInboundMessageSize(Integer.MAX_VALUE)
                    .addService(new GroupService(this))
                    .addService(new RegisterService(this))
                    .addService(new ServerStatsService(this))
                    .addService(new MonitorService(this))
                    .addService(new LogService(this))
                    .build().start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerCommands(airKyrinLibary.getCloudCommandManager());

        processManager = new ProcessManager(this);
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));


    }

    private void registerCommands(CloudCommandManager commandManager) {
        new CreateGroupCommand(commandManager);
        new StopCommand(commandManager);
        new StartServer(commandManager);
    }

    public void stop() {
        deleteTmp();
    }

    private void deleteTmp() {
        Path tmpPath = Paths.get("bee", "tmp");
        if (Files.exists(tmpPath)) {
            try {
                Files.delete(tmpPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateAll() {
        this.fileManager.updateConfigs();
        deleteTmp();
    }

    public static Hive getInstance() {
        return instance;
    }
}
