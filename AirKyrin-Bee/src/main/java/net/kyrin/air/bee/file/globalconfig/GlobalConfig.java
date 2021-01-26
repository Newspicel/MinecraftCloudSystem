package net.kyrin.air.bee.file.globalconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyrin.air.lib.utils.file.container.Config;

@Getter
@Setter
@AllArgsConstructor
public class GlobalConfig extends Config {
    private String name;

    private String key;

    private String hiveIp;

    private int port;

    private int maxMemory;


}
