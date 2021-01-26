package net.kyrin.air.hive.hooks;

import net.kyrin.air.hive.Hive;

public final class ShutdownHook extends Thread {

    private final Hive hive;

    public ShutdownHook(Hive bee) {
        this.hive = bee;
    }

    @Override
    public void run() {
        hive.stop();
    }
}