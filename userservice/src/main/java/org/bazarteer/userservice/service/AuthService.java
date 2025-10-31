package org.bazarteer.userservice.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

import org.bazarteer.userservice.model.AuthType;
import org.bazarteer.userservice.model.User;
import org.bazarteer.userservice.proto.AuthServiceGrpc;
import org.bazarteer.userservice.proto.LoginRequest;
import org.bazarteer.userservice.proto.LoginResponse;
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
        try{
            Optional<User> optionalUser = userRepository.findByUsername(req.getUsername());
            if (optionalUser.isPresent()){
                responseObserver.onError(Status.ALREADY_EXISTS.withDescription("Username is already taken").asRuntimeException());
                return;
            }

            if (req.getGoogleToken() != "") {
                //TODO ko naredis na frontend google auth ga tu daj v bazo z AuthType.GOOGLE
            }

            User user = userRepository.save(
                new User(req.getName(), req.getSurname(), req.getUsername(), passwordEncoder.encode(req.getPassword()),  AuthType.LOCAL, "", "", 0, null)
            );
            RegisterResponse res = RegisterResponse.newBuilder().setUserId(user.getId()).setUsername(user.getUsername()).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        } catch (Exception e){
                responseObserver.onError(
                Status.INTERNAL.withDescription("Internal server error").withCause(e).asRuntimeException()
            );
        }
    }

    @Override
    public void login(LoginRequest req, StreamObserver<LoginResponse> responseObserver){
        try{
            Optional<User> optionalUser = userRepository.findByUsername(req.getUsername());
            if (optionalUser.isEmpty()){
                responseObserver.onError(
                    Status.NOT_FOUND.withDescription("User not found: " + req.getUsername()).asRuntimeException()
                );
                return;
            }
            User user = optionalUser.get();
            if(passwordEncoder.matches(req.getPassword(), user.getPassword())){
            LoginResponse res = LoginResponse.newBuilder().setUserId(user.getId()).setUsername(user.getUsername()).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            }
            else {
                responseObserver.onError(
                    Status.UNAUTHENTICATED.withDescription("Password is invalid for user: " + req.getUsername()).asRuntimeException()
                );
            }
        } catch (Exception e){
            responseObserver.onError(
                Status.INTERNAL.withDescription("Internal server error").withCause(e).asRuntimeException()
            );
        }
    }
}
