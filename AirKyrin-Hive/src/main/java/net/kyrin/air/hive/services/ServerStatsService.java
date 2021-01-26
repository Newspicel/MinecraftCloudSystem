package net.kyrin.air.hive.services;

import io.grpc.stub.StreamObserver;
import net.kyrin.air.hive.Hive;
import net.kyrin.air.hive.Process;
import net.kyrin.air.hive.ServerStats;
import net.kyrin.air.hive.StatsServiceGrpc;

public class ServerStatsService extends StatsServiceGrpc.StatsServiceImplBase {

    private Hive hive;

    public ServerStatsService(Hive hive) {
        this.hive = hive;
    }

    @Override
    public void sendStats(ServerStats request, StreamObserver<Process> responseObserver) {
        Process taskByFreeRam = hive.getProcessManager().getTaskByFreeRam(request.getFreeRam());
        hive.getProcessManager().getTasks().remove(taskByFreeRam);
        responseObserver.onNext(taskByFreeRam);
        responseObserver.onCompleted();
    }
}
