package net.kyrin.air.hive.groups;

import lombok.Getter;
import net.kyrin.air.hive.Hive;
import net.kyrin.air.hive.database.ServerGroupManager;
import net.kyrin.air.hive.database.models.ServerGroup;
import net.kyrin.air.hive.templates.TemplateManager;

@Getter
public class GroupManager {

    private ServerGroupManager serverGroupManager;
    private TemplateManager templateManager;

    public GroupManager() {
        this.serverGroupManager = Hive.getInstance().getServerGroupManager();
        this.templateManager = Hive.getInstance().getTemplateManager();
        // initDefaultGroups();
    }

    public boolean isExist(String name) {
        return getGroup(name) != null;
    }

    public ServerGroup getGroup(String name) {
        return serverGroupManager.get(name);
    }

    public void createGroup(ServerGroup serverGroup) {
        serverGroupManager.insert(serverGroup);
        templateManager.prepareTemplateForGroup(serverGroup);
    }

    public void deleteGroup(ServerGroup serverGroup) {
        serverGroupManager.delete(serverGroup.getName());
    }

    /* private void initDefaultGroups() {
        if (!serverGroupManager.getAll().isEmpty())
            return;

        createGroup(new Proxy());
        createGroup(new Lobby());
    } */
}
