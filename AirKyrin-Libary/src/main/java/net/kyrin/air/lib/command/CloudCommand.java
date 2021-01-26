package net.kyrin.air.lib.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CloudCommand {

    private String name;

    public CloudCommand(CloudCommandManager cloudCommandManager, String name) {
        this.name = name;
        cloudCommandManager.registerCommand(this);
    }

    public abstract void executeCommand(String[] args);

}
