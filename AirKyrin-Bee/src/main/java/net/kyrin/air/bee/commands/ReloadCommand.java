package net.kyrin.air.bee.commands;

import net.kyrin.air.bee.Bee;
import net.kyrin.air.lib.command.CloudCommand;
import net.kyrin.air.lib.command.CloudCommandManager;

public class ReloadCommand extends CloudCommand {


    public ReloadCommand(CloudCommandManager cloudCommandManager) {
        super(cloudCommandManager, "reload");
    }

    @Override
    public void executeCommand(String[] args) {
        Bee.getInstance().updateAll();
    }
}
