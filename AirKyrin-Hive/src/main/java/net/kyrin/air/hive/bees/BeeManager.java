package net.kyrin.air.hive.bees;


import net.kyrin.air.hive.Hive;
import net.kyrin.air.hive.file.beeconfig.BeeConfig;

import java.security.SecureRandom;
import java.util.ArrayList;

public class BeeManager {

    private ArrayList<Bee> bees;

    public BeeManager() {
        bees = Hive.getInstance().getFileManager().getBeeConfigManager().get().getBees();
    }

    public void firstRegisterBee(String name, String inetAddress) {
        String key = generateKey();
        int id = generateID();
        Hive.getInstance().getAirKyrinLibary().getConsoleLogger().info("The Key for your Bee-" + generateID() + " with the name: " + name + "  is: " + key);
        addBeeToConfig(id, name, key, inetAddress);
    }

    public int registerBee(String name, String inetAddress, String key) {
        System.out.println("Test");
        Hive.getInstance().getAirKyrinLibary().getConsoleLogger().info("A Bee is want to connect to the Hive!");
        Bee configBee = getBeeByIp(inetAddress);
        if (configBee == null) {
            Hive.getInstance().getAirKyrinLibary().getConsoleLogger().info("An unknown bee wanted to connect!");
            return -1;
        }

        if (!configBee.getKey().equalsIgnoreCase(key)) {
            Hive.getInstance().getAirKyrinLibary().getConsoleLogger().info("The Bee Key is wrong. Please Edit the Key to: " + configBee.getKey());
            return -1;
        }

        final BeeConfig beeConfig = Hive.getInstance().getFileManager().getBeeConfigManager().get();
        beeConfig.getBees().remove(configBee);
        configBee.setName(name);
        beeConfig.getBees().add(configBee);
        Hive.getInstance().getFileManager().getBeeConfigManager().set(beeConfig);
        Hive.getInstance().getAirKyrinLibary().getConsoleLogger().info("The Bee-" + configBee.getId() + " is successful connect!");
        return configBee.getId();
    }

    private String generateKey() {
        final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789".toCharArray();
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }
        return password.toString();
    }

    private int generateID() {
        final char[] allAllowed = "0123456789".toCharArray();
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }
        return Integer.parseInt(password.toString());
    }

    private void addBeeToConfig(int id, String name, String key, String inetAddress) {
        BeeConfig beeConfig = Hive.getInstance().getFileManager().getBeeConfigManager().get();
        beeConfig.addBee(new Bee(id, name, key, inetAddress));
        Hive.getInstance().getFileManager().getBeeConfigManager().set(beeConfig);
    }

    public Bee getBeeByIp(String inetAddress) {
        return bees.stream().filter(bee -> (bee.getInetAddress().equalsIgnoreCase(inetAddress))).findFirst().orElse(null);
    }

    public ArrayList<Bee> getBees() {
        return bees;
    }
}
