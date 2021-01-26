package net.kyrin.air.bee.hooks;

import net.kyrin.air.bee.Bee;

public final class ShutdownHook extends Thread {

    private final Bee bee;

    public ShutdownHook(Bee bee) {
        this.bee = bee;
    }

    @Override
    public void run() {
        bee.stop();
    }
}