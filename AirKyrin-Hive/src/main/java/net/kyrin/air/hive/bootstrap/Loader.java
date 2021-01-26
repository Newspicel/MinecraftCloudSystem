package net.kyrin.air.hive.bootstrap;


import net.kyrin.air.hive.Hive;

public class Loader {

    public synchronized static void main(String[] args) {
        long current = System.currentTimeMillis();

        System.out.println("Starting the Hive Module...");

        Hive hive = new Hive(args);

        System.out.println("The Hive was started in " + (System.currentTimeMillis() - current) + " milliseconds.");

        hive.getAirKyrinLibary().sendHeader();

        System.out.println("Setting System-Properties...");

        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("client.encoding.override", "UTF-8");

        System.out.println("System-Properties are now ready");

        hive.start();

    }


}
