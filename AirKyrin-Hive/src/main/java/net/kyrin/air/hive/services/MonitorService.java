package net.kyrin.air.hive.services;

import io.grpc.stub.StreamObserver;
import net.kyrin.air.hive.*;
import net.kyrin.air.hive.server.ServerManager;

import java.util.UUID;

public class MonitorService extends MonitorManagerGrpc.MonitorManagerImplBase {

    private Hive hive;

    public MonitorService(Hive hive) {
        this.hive = hive;
    }

    @Override
    public void sendPlayerJoinEvent(PlayerJoinEvent request, StreamObserver<PlayerJoinEventAnswer> responseObserver) {

    }

    @Override
    public void sendPlayerDisconnectEvent(PlayerDisconnectEvent request, StreamObserver<PlayerDisconnectEventAnswer> responseObserver) {

    }

    @Override
    public void sendServerSwitchToIngameEvent(ServerSwitchToIngameEvent request, StreamObserver<ServerSwitchToIngameEventAnswer> responseObserver) {
        hive.getServerManager().switchToIngame(UUID.fromString(request.getServeruuid()));
        responseObserver.onNext(ServerSwitchToIngameEventAnswer.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void setupServer(RegisterServer request, StreamObserver<RegisterServerAnswer> responseObserver) {
        hive.getServerManager().registerServerEvent(UUID.fromString(request.getServeruuid()));
        System.out.println("The Server #" + request.getServeruuid() + " was registered.");
        responseObserver.onNext(RegisterServerAnswer.newBuilder().build());
        responseObserver.onCompleted();
    }
}
