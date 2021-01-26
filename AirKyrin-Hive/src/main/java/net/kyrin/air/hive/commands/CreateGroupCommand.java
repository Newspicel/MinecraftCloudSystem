package net.kyrin.air.hive.commands;

import net.kyrin.air.hive.Hive;
import net.kyrin.air.hive.database.models.ServerGroup;
import net.kyrin.air.lib.command.CloudCommand;
import net.kyrin.air.lib.command.CloudCommandManager;

import java.util.Locale;

public class CreateGroupCommand extends CloudCommand {

    public CreateGroupCommand(CloudCommandManager cloudCommandManager) {
        super(cloudCommandManager, "group");
    }

    @Override
    public void executeCommand(String[] args) {
        switch (args.length) {
            case 2:
                if ("delete".equals(args[0].toLowerCase(Locale.ENGLISH))) {
                    String name = args[1];

                    if (!Hive.getInstance().getGroupManager().isExist(name)) {
                        System.out.println("This group doesn't exist!");
                        return;
                    }

                    Hive.getInstance().getGroupManager().deleteGroup(Hive.getInstance().getGroupManager().getGroup(name));
                    System.out.println("Group " + name + " was successfully deleted!");
                }
                break;

            case 8:
                if (args[0].equalsIgnoreCase("CREATE")) {

                    ServerGroup group = new ServerGroup(
                            args[2],
                            args[1].toUpperCase(),
                            Long.parseLong(args[3]),
                            Long.parseLong(args[4]),
                            Long.parseLong(args[5]),
                            Long.parseLong(args[6]),
                            Boolean.parseBoolean(args[7]),
                            false
                    );

                    if (Hive.getInstance().getGroupManager().isExist(group.getName())) {
                        System.out.println("This group is already exist.");
                        return;
                    }

                    System.out.println("Server group " + group.getName() + " was created and saved!");
                    Hive.getInstance().getGroupManager().createGroup(group);

                }
                break;

            default:
                System.out.println("Commands to manage server / proxy groups:");
                System.out.println("- GROUP CREATE / PROXY <Name, GroupMode, MinOnline, Slots, neededRam, JoinPower, Maintenance>");
                System.out.println("- GROUP DELETE / PROXY <Name>");
                break;
        }

    }
}
