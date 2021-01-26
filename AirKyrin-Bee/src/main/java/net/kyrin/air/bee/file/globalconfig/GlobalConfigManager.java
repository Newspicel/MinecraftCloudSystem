package net.kyrin.air.bee.file.globalconfig;

import com.google.gson.Gson;
import net.kyrin.air.lib.utils.file.container.ConfigManager;

import java.io.File;
import java.nio.file.Path;

public class GlobalConfigManager extends ConfigManager<GlobalConfig> {


    public GlobalConfigManager(Path configFile, Gson gson) {
        super(configFile, gson, GlobalConfig.class);
    }

    @Override
    protected GlobalConfig getDefaults() {
        return new GlobalConfig("BeeName", "Type in here the Key", "localhost", 3333, 0);
    }
}
