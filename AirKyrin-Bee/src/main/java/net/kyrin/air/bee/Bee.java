package net.kyrin.air.bee;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Getter;
import net.kyrin.air.bee.commands.ReloadCommand;
import net.kyrin.air.bee.commands.StopCommand;
import net.kyrin.air.bee.file.FileManager;
import net.kyrin.air.bee.file.globalconfig.GlobalConfig;
import net.kyrin.air.bee.hive.HiveManager;
import net.kyrin.air.bee.hooks.ShutdownHook;
import net.kyrin.air.bee.process.ProcessManager;
import net.kyrin.air.lib.AirKyrinLibary;
import net.kyrin.air.lib.command.CloudCommandManager;
import net.kyrin.air.lib.command.CommandLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public class Bee {

    public static boolean RUNNING = false;
    public static boolean FIRSTSTART = false;
    private static Bee instance;
    private UUID uuid;

    private AirKyrinLibary airKyrinLibary;
    private FileManager fileManager;

    private ManagedChannel managedChannel;
    private HiveManager hiveManager;

    private ProcessManager processManager;

    public Bee(String[] args, String loggerName, UUID uuid) {
        instance = this;
        this.uuid = uuid;
        airKyrinLibary = new AirKyrinLibary("bee", loggerName);
        fileManager = new FileManager();
        new Thread(new CommandLoader()).start();
    }

    public void start() {
        openConnectionToHive();
        downloadTemplates(managedChannel);
        registerCommands(airKyrinLibary.getCloudCommandManager());
        processManager = new ProcessManager(this);
        Timer timer = new Timer("Servers");
        timer.schedule(processManager, 0, 100);
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));
    }

    public void stop() {
        managedChannel.shutdown();
        try {
            managedChannel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processManager.getProcesses().forEach(java.lang.Process::destroy);
        deleteTmp();
    }

    public static Bee getInstance() {
        return instance;
    }

    private void openConnectionToHive() {
        final GlobalConfig globalConfig = fileManager.getGlobalConfigManager().get();
        if (FIRSTSTART) {
            final Scanner scanner = new Scanner(System.in);
            airKyrinLibary.getConsoleLogger().info("What is the ip of the Hive");
            globalConfig.setHiveIp(scanner.nextLine().replaceAll(" ", "").replaceAll("\n", ""));
            airKyrinLibary.getConsoleLogger().info("What is the port of the Hive");
            globalConfig.setPort(Integer.parseInt(scanner.nextLine().replaceAll(" ", "").replaceAll("\n", "")));
            fileManager.getGlobalConfigManager().set(globalConfig);
            managedChannel = ManagedChannelBuilder.forAddress(globalConfig.getHiveIp(), globalConfig.getPort()).maxInboundMessageSize(Integer.MAX_VALUE).usePlaintext().build();
            hiveManager = new HiveManager(this);
            hiveManager.firstRegister();
        } else {
            managedChannel = ManagedChannelBuilder.forAddress(globalConfig.getHiveIp(), globalConfig.getPort()).maxInboundMessageSize(Integer.MAX_VALUE).usePlaintext().build();
            hiveManager = new HiveManager(this);
            hiveManager.register();
        }
    }

    private void downloadTemplates(ManagedChannel managedChannel) {
        final GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub = GroupServiceGrpc.newBlockingStub(managedChannel);
        final AllGroups groupNames = groupServiceBlockingStub.getAllGroupNames(RequestAllGroups.newBuilder().build());
        System.out.println(groupNames.getNameCount());
        groupNames.getNameList().forEach(t -> {
            final Group group = groupServiceBlockingStub.sendGroupFiles(GroupRequest.newBuilder().setName(t).build());
            final File content = group.getContent();
            final String name = group.getName();
            final Path tmpFolder = Paths.get("bee", "tmp", "downloads");
            final Path tmp = Paths.get(tmpFolder.toAbsolutePath().toString(), name + ".zip");
            try {
                if (!Files.exists(tmpFolder)) {
                    Files.createDirectories(tmpFolder);
                }
                if (!Files.exists(tmp)) {
                    Files.createFile(tmp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Files.write(tmp, content.getBytes().toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateAll() {
        fileManager.updateFiles();
        deleteTmp();
        downloadTemplates(managedChannel);
    }

    private void deleteTmp() {
        Path tmpPath = Paths.get("bee", "tmp");
        if (Files.exists(tmpPath)) {
            final java.io.File dir = tmpPath.toFile();
            if (dir.isDirectory()) {
                java.io.File[] children = dir.listFiles();
                for (java.io.File child : children) {
                    deleteDirectory(child);
                }
            }
            dir.delete();
        }
    }

    private void deleteDirectory(java.io.File dir) {
        if (dir.isDirectory()) {
            java.io.File[] children = dir.listFiles();
            for (java.io.File child : children) {
                deleteDirectory(child);
            }
        }
        dir.delete();
    }


    private void registerCommands(CloudCommandManager commandManager) {
        new StopCommand(commandManager);
        new ReloadCommand(commandManager);
    }
}
