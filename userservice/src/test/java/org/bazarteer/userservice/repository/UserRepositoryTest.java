package org.bazarteer.userservice.repository;

import org.bazarteer.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import java.util.List;
import java.util.Optional;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsernameRegex() {
        List<User> users = userRepository.findByUsernameFuzz("mar");
        users.forEach(System.out::println);
    }

    @Test
    void testFindByUsername()  {
        Optional<User> user = userRepository.findById("68fc0e3e496cc2e1cacb8723");
        System.out.println(user.toString());
    }

    /*@Test
    void testSave()  {
        User user = userRepository.save(new User("nek2i_id", "Novo2Ime", "NovPri2imek", "user2namee", "bu2iii", "imag2eee", 0, null));
        System.out.println(user.toString());
    }*/
}
