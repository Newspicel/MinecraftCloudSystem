package net.kyrin.air.hive.server;

import net.kyrin.air.hive.server.models.Server;
import net.kyrin.air.hive.server.models.ServerMode;

import java.util.ArrayList;
import java.util.UUID;

public class ServerManager {

    private ArrayList<Server> servers;

    public ServerManager() {
        this.servers = new ArrayList<>();
    }

    public void addServer(UUID uuid){
        Server server = new Server(uuid, ServerMode.STARTING);
        servers.add(server);
    }

    public void registerServerEvent(UUID uuid){
        servers.remove(getServers().stream().filter(server -> server.getUuid().toString().equalsIgnoreCase(uuid.toString())).findFirst().orElse(new Server(UUID.randomUUID(), ServerMode.NULL)));
        servers.add(new Server(uuid, ServerMode.ONLINE));
    }

    public void switchToIngame(UUID uuid){
        servers.remove(getServers().stream().filter(server -> server.getUuid().toString().equalsIgnoreCase(uuid.toString())).findFirst().orElse(new Server(UUID.randomUUID(), ServerMode.NULL)));
        servers.add(new Server(uuid, ServerMode.HIDDEN));
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
