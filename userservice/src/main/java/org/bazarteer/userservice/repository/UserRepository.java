package org.bazarteer.userservice.repository;
import java.util.List;
import java.util.Optional;

import org.bazarteer.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;



public interface UserRepository extends MongoRepository<User, String>{

    public <S extends User> S save(S user);

    public Optional<User> findById(String Id);

    @Query("{ 'username': ?0 }")
    public Optional<User> findByUsername(String username);

    @Query("{ 'username': {$regex: ?0, $options: 'i'} }")
    public List<User> findByUsernameFuzz(String username);
}
