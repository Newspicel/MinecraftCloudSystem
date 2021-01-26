package net.kyrin.air.hive.bees;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Bee {

    private int id;

    private String name;

    private String key;

    private String inetAddress;

}