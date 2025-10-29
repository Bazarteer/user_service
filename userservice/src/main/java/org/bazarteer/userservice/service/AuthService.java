package org.bazarteer.userservice.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import org.bazarteer.userservice.model.AuthType;
import org.bazarteer.userservice.model.User;
import org.bazarteer.userservice.proto.AuthServiceGrpc;
import org.bazarteer.userservice.proto.RegisterRequest;
import org.bazarteer.userservice.proto.RegisterResponse;
import org.bazarteer.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase{

    @Autowired
    private  UserRepository userRepository;
    
    @Autowired
    private  PasswordEncoder passwordEncoder;


    @Override
    public void register(RegisterRequest req, StreamObserver<RegisterResponse> responseObserver) {
        if (req.getGoogleToken() != "") {
            //nesto
        }
        
        User user = userRepository.save(
            new User(req.getName(), req.getSurname(), req.getUsername(), passwordEncoder.encode(req.getPassword()),  AuthType.LOCAL, "", "", 0, null)
        );
        
        RegisterResponse res = RegisterResponse.newBuilder().setUserId(user.getId()).setUsername(user.getUsername()).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
