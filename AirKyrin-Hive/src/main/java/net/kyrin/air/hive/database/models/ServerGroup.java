package net.kyrin.air.hive.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerGroup {

    private String name;
    private String groupMode;
    private Long minOnlineServers;
    private Long serverSlots;
    private Long joinPower;
    private Long neededRam;
    private Boolean groupMaintenance;
    private Boolean isProxy;

}
