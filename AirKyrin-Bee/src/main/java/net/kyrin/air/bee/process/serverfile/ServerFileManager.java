package net.kyrin.air.bee.process.serverfile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.kyrin.air.bee.process.serverfile.globalconfig.GlobalConfigManager;
import net.kyrin.air.lib.utils.file.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ServerFileManager {

    private Gson gson;

    public ServerFileManager() {
        this.gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
    }

    public void updateFiles(UUID uuid){
        getGlobalConfigManager(uuid).update();
    }

    public Path getConfigPath(UUID uuid){
        Path path = Paths.get("bee", "tmp", uuid.toString(), "plugins", "AirKyrinAPI");
        FileUtils.createPath(path);
        return path;
    }

    public GlobalConfigManager getGlobalConfigManager(UUID uuid) {
        Path global = FileUtils.getPathByName(getConfigPath(uuid), "global");
        return new GlobalConfigManager(global, gson);
    }
}
