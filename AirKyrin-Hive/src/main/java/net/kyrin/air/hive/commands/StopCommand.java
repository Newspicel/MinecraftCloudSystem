package net.kyrin.air.hive.commands;

import net.kyrin.air.lib.command.CloudCommand;
import net.kyrin.air.lib.command.CloudCommandManager;

public class StopCommand extends CloudCommand {


    public StopCommand(CloudCommandManager cloudCommandManager) {
        super(cloudCommandManager, "stop");
    }

    @Override
    public void executeCommand(String[] args) {
        System.exit(0);
    }
}
