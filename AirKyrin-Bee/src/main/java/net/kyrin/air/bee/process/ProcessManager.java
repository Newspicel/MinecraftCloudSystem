package net.kyrin.air.bee.process;

import net.kyrin.air.bee.*;
import net.kyrin.air.bee.Process;
import net.kyrin.air.bee.file.globalconfig.GlobalConfig;
import net.kyrin.air.bee.process.serverfile.ServerFileManager;
import net.kyrin.air.bee.process.serverfile.globalconfig.GlobalConfigManager;
import net.kyrin.air.lib.document.DocumentManager;
import net.kyrin.air.lib.utils.file.FileUtils;
import net.kyrin.air.lib.utils.port.PortCheck;
import org.zeroturnaround.zip.ZipUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.UUID;

public class ProcessManager extends TimerTask {

    private StatsServiceGrpc.StatsServiceBlockingStub statsServiceBlockingStub;
    private ServerFileManager serverFileManager;
    private Bee bee;
    private ArrayList<java.lang.Process> processes = new ArrayList<>();
    private PortCheck portCheck;

    public ProcessManager(Bee bee) {
        this.bee = bee;
        statsServiceBlockingStub = StatsServiceGrpc.newBlockingStub(bee.getManagedChannel());
        serverFileManager = new ServerFileManager();
        portCheck = new PortCheck();
    }

    @Override
    public void run() {
        if (statsServiceBlockingStub != null) {
            Process process = statsServiceBlockingStub.sendStats(ServerStats.newBuilder().setOnlineServer(StatsManager.getOnlineServer()).setFreeRam(StatsManager.getFreeRam()).setRealFreeRam(StatsManager.getRealFreeRam()).build());
            if (!DocumentManager.getString(process.getServerConfig(), "name").equalsIgnoreCase("null")/*process.getAllFields().size() != 0*/) {
                System.out.println("Start Server");
                Path tmpPath = Paths.get("bee", "tmp");
                Path downLoadPath = Paths.get(tmpPath.toString(), "downloads");
                Path zipFolder = Paths.get(downLoadPath.toString(), DocumentManager.getString(process.getServerConfig(), "name") + ".zip");
                Path folder = Paths.get(tmpPath.toString(), process.getUuid());
                Path jarFile = Paths.get(folder.toString(), "server.jar");
                FileUtils.createPath(tmpPath);
                ZipUtil.unpack(zipFolder.toFile(), folder.toFile());
                StatsManager.addServer(process.getNeedRam());
                GlobalConfigManager globalConfigManager = serverFileManager.getGlobalConfigManager(UUID.fromString(process.getUuid()));
                net.kyrin.air.bee.process.serverfile.globalconfig.GlobalConfig globalConfig = globalConfigManager.get();
                GlobalConfig globalConfig1 = bee.getFileManager().getGlobalConfigManager().get();
                globalConfig.setHiveIp(globalConfig1.getHiveIp());
                globalConfig.setHivePort(globalConfig1.getPort());
                globalConfig.setServerUUID(process.getUuid());
                globalConfigManager.set(globalConfig);
                int port = portCheck.getRandomPort();
                try {
                    final String command = "java -javaagent:slimeworldmanager-classmodifier-2.2.1.jar -Xms" + process.getNeedRam() + "m -Xmx" + process.getNeedRam() + "m -Dcom.mojang.eula.agree=true -jar server.jar nogui --port " + port + "";
                    java.lang.Process exec = Runtime.getRuntime().exec(command, null, folder.toFile());
                    processes.add(exec);
                   /* String readLine;
                    BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                    while (((readLine = br.readLine()) != null)) {
                        LogServiceGrpc.newBlockingStub(bee.getManagedChannel()).sendLogMessage(Log.newBuilder().setUuid(process.getUuid()).setBeeUUID(bee.getUuid().toString()).setMessage(readLine).setTime(System.currentTimeMillis()).build());
                    }*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<java.lang.Process> getProcesses() {
        return processes;
    }


}
