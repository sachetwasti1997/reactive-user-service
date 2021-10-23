package com.sachet.userservice;

import com.sachet.userservice.entity.User;
import com.sachet.userservice.entity.UserTransaction;
import com.sachet.userservice.repository.UserRepository;
import com.sachet.userservice.repository.UserTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

public class UserTransactionTest extends UserserviceApplicationTests {

    @Autowired
    UserTransactionRepository userTransactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void deleteAll(){
        userTransactionRepository
                .deleteAll()
                .subscribe();

        userRepository
                .deleteAll()
                .subscribe();
    }

    public User createUser(String name, Double amount){
        User user = new User();
        user.setName(name);
        user.setBalance(amount);
        return userRepository
                .save(user)
                .block();
    }

    @Test
    public void createUserTransaction(){
        User user = createUser("Sachet", 12000.00);

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(user.getId());
        userTransaction.setAmount(13000.00);
        userTransaction.setTransactionDate(LocalDateTime.now());

        WebTestClient.ResponseSpec responseSpec = webTestClient
                .post()
                .uri("/user_transaction/create")
                .bodyValue(userTransaction)
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

}




















