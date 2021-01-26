package net.kyrin.air.hive.services;

import io.grpc.stub.StreamObserver;
import net.kyrin.air.hive.Hive;
import net.kyrin.air.hive.Log;
import net.kyrin.air.hive.LogAnswer;
import net.kyrin.air.hive.LogServiceGrpc;
import net.kyrin.air.hive.database.LogManager;
import net.kyrin.air.hive.database.models.LogEntry;

public class LogService extends LogServiceGrpc.LogServiceImplBase {

    private Hive hive;
    private LogManager logManager;

    public LogService(Hive hive) {
        this.hive = hive;
        this.logManager = hive.getLogManager();
    }

    @Override
    public void sendLogMessage(Log request, StreamObserver<LogAnswer> responseObserver) {

        LogEntry logEntry = new LogEntry(request.getTime(), request.getUuid(), request.getBeeuuid(), request.getMessage());
        logManager.insert(logEntry);
        System.out.println(logEntry.getMessage());
        responseObserver.onNext(LogAnswer.newBuilder().build());
        responseObserver.onCompleted();
    }

}
