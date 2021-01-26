package net.kyrin.air.hive.services;

import io.grpc.stub.StreamObserver;
import net.kyrin.air.hive.*;

public class RegisterService extends RegisterServiceGrpc.RegisterServiceImplBase {

    private final Hive hive;

    public RegisterService(Hive hive) {
        this.hive = hive;
    }

    @Override
    public void sendFirstRegister(FirstRegister request, StreamObserver<FirstRegisterAnswer> responseObserver) {
        hive.getBeeManager().firstRegisterBee(request.getName(), request.getInetAddress());
        responseObserver.onNext(FirstRegisterAnswer.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void sendRegister(Register request, StreamObserver<RegisterAnswer> responseObserver) {
        int id = hive.getBeeManager().registerBee(request.getName(), request.getInetAddress(), request.getKey());
        responseObserver.onNext(RegisterAnswer.newBuilder().setId(id).build());
        responseObserver.onCompleted();
    }
}
