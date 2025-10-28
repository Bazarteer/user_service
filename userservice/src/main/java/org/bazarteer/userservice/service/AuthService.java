package org.bazarteer.userservice.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.bazarteer.userservice.proto.AuthServiceGrpc;
import org.bazarteer.userservice.proto.RegisterRequest;
import org.bazarteer.userservice.proto.RegisterResponse;

@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase{

    @Override
    public void register(RegisterRequest req, StreamObserver<RegisterResponse> responseObserver) {
        RegisterResponse res = RegisterResponse.newBuilder().setUserId("123").build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
