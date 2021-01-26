package net.kyrin.air.api.airkyrinapi;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.kyrin.air.api.airkyrinapi.file.FileManager;
import net.kyrin.air.api.airkyrinapi.file.globalconfig.GlobalConfig;
import net.kyrin.air.api.hive.MonitorManagerGrpc;
import net.kyrin.air.api.hive.MonitorManagerOuterClass;
import net.kyrin.air.api.hive.RegisterServer;
import net.kyrin.air.api.hive.ServerSwitchToIngameEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class AirKyrinAPI extends JavaPlugin {

    private ManagedChannel managedChannel;
    private FileManager fileManager;

    @Override
    public void onEnable() {
        fileManager = new FileManager();
        openConnectionToHive();
    }

    @Override
    public void onDisable() {
    }

    private void openConnectionToHive() {
        GlobalConfig globalConfig = fileManager.getGlobalConfigManager().get();
        managedChannel = ManagedChannelBuilder.forAddress(globalConfig.getHiveIp(), globalConfig.getHivePort()).maxInboundMessageSize(Integer.MAX_VALUE).usePlaintext().build();
        MonitorManagerGrpc.MonitorManagerBlockingStub monitorManagerBlockingStub = MonitorManagerGrpc.newBlockingStub(managedChannel);
        monitorManagerBlockingStub.setupServer(RegisterServer.newBuilder().setServeruuid(globalConfig.getServerUUID()).build());
    }

    public ManagedChannel getManagedChannel() {
        return managedChannel;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
