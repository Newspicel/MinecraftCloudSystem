package net.kyrin.air.hive.file.beeconfig;

import com.google.gson.Gson;
import net.kyrin.air.lib.utils.file.container.ConfigManager;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class BeeConfigManager extends ConfigManager<BeeConfig> {

    public BeeConfigManager(Path configFile, Gson gson) {
        super(configFile, gson, BeeConfig.class);
    }

    @Override
    protected BeeConfig getDefaults() {
        return new BeeConfig(new ArrayList<>());
    }
}
