package net.kyrin.air.bee.process.serverfile.globalconfig;

import com.google.gson.Gson;
import net.kyrin.air.lib.utils.file.container.ConfigManager;

import java.nio.file.Path;

public class GlobalConfigManager extends ConfigManager<GlobalConfig> {

    public GlobalConfigManager(Path configFile, Gson gson) {
        super(configFile, gson, GlobalConfig.class);
    }

    @Override
    protected GlobalConfig getDefaults() {
        return new GlobalConfig("localhost", 3333, "null");
    }
}
