package net.kyrin.air.hive.process;


import net.kyrin.air.hive.Hive;
import net.kyrin.air.hive.Process;
import net.kyrin.air.hive.database.models.ServerGroup;
import net.kyrin.air.lib.document.DocumentManager;
import net.kyrin.air.lib.document.models.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessManager {

    private Hive hive;

    private List<Process> tasks = new ArrayList<>();

    public ProcessManager(Hive hive) {
        this.hive = hive;
    }

    public void startServer(ServerGroup serverGroup, Document document){
        DocumentManager.Builder builder = DocumentManager.newBuilder();
        builder.setString("name", serverGroup.getName());
        builder.setString("groupMode", serverGroup.getGroupMode());
        builder.setInteger("minOnlineServers", Math.toIntExact(serverGroup.getMinOnlineServers()));
        builder.setInteger("serverSlots", Math.toIntExact(serverGroup.getServerSlots()));
        builder.setInteger("joinPower", Math.toIntExact(serverGroup.getJoinPower()));
        builder.setInteger("neededRam", Math.toIntExact(serverGroup.getNeededRam()));
        builder.setBoolean("groupMaintenance", serverGroup.getGroupMaintenance());
        builder.setBoolean("proxy", serverGroup.getIsProxy());
        Document serverConfig = builder.build();
        UUID uuid = UUID.randomUUID();
        Process process = Process.newBuilder().setServerConfig(serverConfig).setDocument(document).setNeedRam(Math.toIntExact(serverGroup.getNeededRam())).setUuid(uuid.toString()).build();
        hive.getServerManager().addServer(uuid);
        tasks.add(process);

    }

    public List<Process> getTasks() {
        return tasks;
    }

    public Process getTaskByFreeRam(int freeRam){
        return tasks.stream().findFirst().orElse(Process.newBuilder().setServerConfig(DocumentManager.newBuilder().setString("name", "null").build()).build());
    }
}
