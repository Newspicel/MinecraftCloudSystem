package net.kyrin.air.lib.utils.port;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class PortCheck {
    private int minPort = 10000;
    private int maxPort = 20000;
    Random r = new Random();

    public boolean isAvailable(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    public int getRandomPort() {
        int port = r.nextInt((maxPort - minPort) + 1) + minPort;
        while (!isAvailable(port)){
            port = r.nextInt((maxPort - minPort) + 1) + minPort;
        }
        return port;
    }


}
