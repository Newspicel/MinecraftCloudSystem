package net.kyrin.air.api.airkyrinapi.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.kyrin.air.api.airkyrinapi.file.globalconfig.GlobalConfigManager;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class FileManager {

    private Gson gson;
    private Path configPath;
    private GlobalConfigManager globalConfigManager;

    public FileManager() {
        this.gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        this.configPath = Paths.get("plugins", "AirKyrinAPI");

        FileUtils.createPath(configPath);

        this.globalConfigManager = new GlobalConfigManager(FileUtils.getPathByName(configPath, "global"), gson);

    }

    public void updateFiles(){
        globalConfigManager.update();
    }
}
