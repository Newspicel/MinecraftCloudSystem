package net.kyrin.air.hive.server.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Server {

    private UUID uuid;

    private ServerMode serverMode;




}
