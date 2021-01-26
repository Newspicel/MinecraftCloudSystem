package net.kyrin.air.hive.templates;

import lombok.Getter;
import net.kyrin.air.hive.database.models.ServerGroup;
import net.kyrin.air.lib.utils.file.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class TemplateManager {

    public TemplateManager() {
        Path templateFolder = Paths.get("hive", "template");
        FileUtils.createPath(templateFolder);
    }

    public boolean createMinecraftServerResourcesFolder() {
        Path spigotPath = Paths.get("hive", "config", "spigot");
        Path bungeePath = Paths.get("hive", "config", "bungee");

        FileUtils.createPath(spigotPath);
        FileUtils.createPath(bungeePath);


        boolean spigot = false, bungee = false;

        if (!Files.exists(Paths.get(spigotPath.toAbsolutePath().toString(), "spigot-1.12.2.jar"))) {
            try {
                System.out.println("Start downloading Spigot...");
                FileUtils.downloadFileFromUrl("https://cdn.getbukkit.org/spigot/spigot-1.12.2.jar",
                        "hive/config/spigot/spigot-1.12.2.jar");
                System.out.println("Spigot successfully downloaded!");
                spigot = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Files.exists(Paths.get(bungeePath.toAbsolutePath().toString(), "BungeeCord.jar"))) {
            try {
                System.out.println("Start downloading BungeeCord...");
                FileUtils.downloadFileFromUrl("https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar",
                        "hive/config/bungee/BungeeCord.jar");
                System.out.println("BungeeCord successfully downloaded!");
                bungee = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return spigot && bungee;
    }

    public void prepareTemplateForGroup(ServerGroup serverGroup) {

        Path template = Paths.get("hive", "template", serverGroup.getName().toLowerCase());

        if (!Files.exists(template)) {
            FileUtils.createFile(template);

            if (serverGroup.getIsProxy()) {
                FileUtils.copyFile(new File("hive/config/bungee/BungeeCord.jar").toPath(), template);
            } else {
                FileUtils.copyFile(new File("hive/config/spigot/spigot-1.12.2.jar").toPath(), template);
            }

            System.out.println("Template for group (" + serverGroup.getName() + ") was created!");
        }
    }
}
