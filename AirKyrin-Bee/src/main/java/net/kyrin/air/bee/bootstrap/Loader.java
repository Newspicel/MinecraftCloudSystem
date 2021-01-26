package net.kyrin.air.bee.bootstrap;

import net.kyrin.air.bee.Bee;
import net.kyrin.air.lib.utils.file.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Loader {

    public static void main(String[] args) {
        long current = System.currentTimeMillis();

        System.out.println("Trying to init AirBee");

        UUID uuid = UUID.randomUUID();
        String loggerName = "Bee#" + uuid.toString();

        Bee bee = new Bee(args, loggerName, uuid);

        System.out.println("AirBee was inited in " + (System.currentTimeMillis() - current) + " milliseconds");
        System.out.println("Setting System-Properties...");
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("client.encoding.override", "UTF-8");
        System.out.println("System-Properties are now ready");

        Path firstStartFilePath = Paths.get(".firststart");

        if (!Files.exists(firstStartFilePath)) {
            Bee.FIRSTSTART = true;
            FileUtils.createFile(firstStartFilePath);
        }

        bee.start();

    }


}
