package net.kyrin.air.bee.hive;


import net.kyrin.air.bee.*;
import net.kyrin.air.bee.file.globalconfig.GlobalConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class HiveManager {

    private final Bee bee;

    public HiveManager(Bee bee) {
        this.bee = bee;
    }

    public void register(){
        try {
            final GlobalConfig globalConfig = bee.getFileManager().getGlobalConfigManager().get();
            final RegisterAnswer registerServiceBlockingStub = RegisterServiceGrpc.newBlockingStub(bee.getManagedChannel())
                    .sendRegister(Register.newBuilder().setInetAddress(InetAddress.getLocalHost().toString()).setKey(globalConfig.getKey()).setName(globalConfig.getName()).build());
            if (registerServiceBlockingStub.getId() == -1){
                bee.getAirKyrinLibary().getConsoleLogger().info("No Auth");
                bee.stop();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void firstRegister(){
        try {
            System.out.println("What is the name from This Bee?");
            final String value = new Scanner(System.in).nextLine();
            final FirstRegisterAnswer registerServiceBlockingStub = RegisterServiceGrpc.newBlockingStub(bee.getManagedChannel())
                    .sendFirstRegister(FirstRegister.newBuilder().setInetAddress(InetAddress.getLocalHost().toString()).setName(value).build());
            final GlobalConfig object = bee.getFileManager().getGlobalConfigManager().get();
            object.setName(value);
            bee.getFileManager().getGlobalConfigManager().set(object);
            bee.stop();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
