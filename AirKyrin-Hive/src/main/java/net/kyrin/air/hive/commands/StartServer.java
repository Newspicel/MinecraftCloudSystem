package net.kyrin.air.hive.commands;

import net.kyrin.air.hive.Hive;
import net.kyrin.air.lib.command.CloudCommand;
import net.kyrin.air.lib.command.CloudCommandManager;
import net.kyrin.air.lib.document.models.Document;

public class StartServer extends CloudCommand {

    public StartServer(CloudCommandManager cloudCommandManager) {
        super(cloudCommandManager, "start");
    }

    @Override
    public void executeCommand(String[] args) {
        Hive.getInstance().getProcessManager().startServer(Hive.getInstance().getServerGroupManager().get("lobby"), Document.newBuilder().build());
    }
}
