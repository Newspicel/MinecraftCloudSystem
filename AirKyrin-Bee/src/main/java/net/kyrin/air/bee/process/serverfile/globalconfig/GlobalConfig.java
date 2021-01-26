package net.kyrin.air.bee.process.serverfile.globalconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyrin.air.lib.utils.file.container.Config;

@Getter
@Setter
@AllArgsConstructor
public class GlobalConfig extends Config {
    private String hiveIp;
    private Integer hivePort;
    private String serverUUID;
}
