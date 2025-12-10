package org.bazarteer.userservice.service;

import java.util.Optional;

import org.bazarteer.userservice.model.ProductPublishedMessage;
import org.bazarteer.userservice.model.OrderPlacedMessage;
import org.bazarteer.userservice.model.User;
import org.bazarteer.userservice.proto.UserServiceGrpc;
import org.bazarteer.userservice.proto.UpdateRequest;
import org.bazarteer.userservice.proto.UpdateResponse;
import org.bazarteer.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void update(UpdateRequest req, StreamObserver<UpdateResponse> responseObserver) {
        try {
            Optional<User> optionalUser = userRepository.findById(req.getUserId());
            if (optionalUser.isEmpty()) {
                responseObserver.onError(Status.NOT_FOUND.withDescription("User not found").asRuntimeException());
                return;
            }

            User user = optionalUser.get();
            if (!req.getImage().isEmpty())
                user.setImage(req.getImage());
            if (!req.getBio().isEmpty())
                user.setBio(req.getBio());

            userRepository.save(user);

            UpdateResponse res = UpdateResponse.newBuilder().setUserId(user.getId()).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.out.println(e);
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Internal server error").withCause(e).asRuntimeException());
        }
    }

    public void handleProductPublished(ProductPublishedMessage message) {
        System.out.println("Test uspesen, dobil: " + message.getName());
    }

    public void handleOrderPlaced(OrderPlacedMessage message) {
        try{
            Optional<User> optionalUser = userRepository.findById(message.getSellerId());
            if (optionalUser.isEmpty()){
                return;
            }
            User user = optionalUser.get();
            int num_sales = user.getNum_sales();
            user.setNum_sales(num_sales + 1);
            userRepository.save(user);
        } catch (Exception e){
            System.out.println("Exception for user: " + message.getSellerId() + e);
        }
    }

}
