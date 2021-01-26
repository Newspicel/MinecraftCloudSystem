package net.kyrin.air.api.airkyrinapi.file.globalconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyrin.air.api.airkyrinapi.file.container.Config;

@Getter
@Setter
@AllArgsConstructor
public class GlobalConfig extends Config {
    private String hiveIp;
    private Integer hivePort;
    private String serverUUID;
}
