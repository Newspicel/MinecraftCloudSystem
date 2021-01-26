package net.kyrin.air.hive.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.kyrin.air.hive.file.beeconfig.BeeConfigManager;
import net.kyrin.air.hive.file.globalconfig.GlobalConfigManager;
import net.kyrin.air.lib.utils.file.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class FileManager {

    private Gson gson;

    private Path configPath;

    private GlobalConfigManager globalConfigManager;
    private BeeConfigManager beeConfigManager;

    public FileManager() {
        this.gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();

        this.configPath = Paths.get("hive", "config");

        FileUtils.createPath(this.configPath);

        this.globalConfigManager = new GlobalConfigManager(FileUtils.getPathByName(configPath, "global"), gson);
        this.beeConfigManager = new BeeConfigManager(FileUtils.getPathByName(configPath, "bees"), gson);
    }

    public void updateConfigs(){
        globalConfigManager.update();
        beeConfigManager.update();
    }
}
