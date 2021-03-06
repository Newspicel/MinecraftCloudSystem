package net.kyrin.air.hive.file.globalconfig;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyrin.air.lib.utils.file.container.Config;

@Getter
@Setter
@AllArgsConstructor
public class GlobalConfig extends Config {

    private int port;
    private String databaseUser;
    private String databasePassword;

}
