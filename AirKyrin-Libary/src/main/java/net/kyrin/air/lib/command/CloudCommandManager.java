package net.kyrin.air.lib.command;

import java.util.ArrayList;
import java.util.List;

public final class CloudCommandManager {

    private final List<CloudCommand> commands = new ArrayList<>();

    CloudCommand getCommand(String name) {
        return commands.stream()
                .filter(cloudCommand -> cloudCommand.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public void registerCommand(CloudCommand command) {
        this.commands.add(command);
    }

    public List<CloudCommand> getCommands() {
        return commands;
    }
}
