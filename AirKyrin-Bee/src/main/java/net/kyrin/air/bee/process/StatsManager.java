package net.kyrin.air.bee.process;

public class StatsManager {

    private static Integer onlineServer = 0;

    private static Integer freeRam = 0;

    public static void addServer(Integer neededRam){
        onlineServer++;
        freeRam = freeRam + neededRam;
    }

    public static void removeServer(Integer neededRam){
        onlineServer--;
        freeRam = freeRam - neededRam;
    }

    public static Integer getOnlineServer() {
        return onlineServer;
    }

    public static Integer getFreeRam() {
        return freeRam;
    }

    public static Integer getRealFreeRam(){
        return Math.toIntExact(Runtime.getRuntime().freeMemory() / 1048576);
    }
}
