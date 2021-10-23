package com.sachet.userservice;

import com.sachet.userservice.entity.User;
import com.sachet.userservice.repository.UserRepository;
import com.sachet.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class UserTest extends UserserviceApplicationTests{

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    WebTestClient webTestClient;

    @AfterEach
    public void deleteAll(){
        userRepository.deleteAll();
    }

    private Mono<User> createUserMono(User user){
        return Mono.just(user);
    }

    private User createUser(String name, Double balance){
        User user = new User();
        user.setName(name);
        user.setBalance(balance);
        return user;
    }

    private Mono<User> saveUser(User user){
        return userService
                .saveUser(Mono.just(user));
    }

    @BeforeEach
    public void clearAll(){
        userRepository
                .deleteAll()
                .subscribe();
    }

    @Test
    public void createUserUserValidReceiveOk(){

        User userMono = createUser("Sachet", 12000.00);

        WebTestClient.ResponseSpec responseSpec = webTestClient
                .post()
                .uri("/user/save")
                .bodyValue(userMono)
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();

    }

    @Test
    public void createUserNameNullReceiveBadRequest(){
        User user = createUser(null, 12.00);

        WebTestClient.ResponseSpec responseSpec = webTestClient
                .post()
                .uri("/user/save")
                .bodyValue(user)
                .exchange();

        responseSpec
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void findProductByIdReceiveOk(){
        User user = saveUser(createUser("Shital", 13000.00)).block();

        WebTestClient.ResponseSpec responseSpec =  webTestClient
                        .get()
                        .uri("/user/"+user.getId())
                        .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void findProductByIdProductNotExistReceiveNotFound(){
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .get()
                .uri("/user/123")
                .exchange();

        responseSpec
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void findAllUsers(){
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .get()
                .uri("/user")
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteUserByIdReceiveOk(){
        User user = saveUser(createUser("Sanjeev", 14000.00)).block();

        WebTestClient.ResponseSpec responseSpec = webTestClient
                .delete()
                .uri("/user/delete/{id}", user.getId())
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteUserByIdUserNotExistReceiveNotFound(){
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .delete()
                .uri("/user/delete/5")
                .exchange();

        responseSpec
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void deleteUserReceiveOk(){
        Mono<User> user = saveUser(createUser("Sanjeev", 14000.00));

        WebTestClient.ResponseSpec responseSpec = webTestClient
                .method(HttpMethod.DELETE)
                .uri("/user/delete")
                .body(user, User.class)
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteUserUserNotExistReceiveNotExist(){
        User user = createUser("Srishti", 12000.00);
        user.setId(12);

        Mono<User> userMono = Mono.just(user);

        WebTestClient.ResponseSpec responseSpec = webTestClient
                .method(HttpMethod.DELETE)
                .uri("/user/delete")
                .body(userMono, User.class)
                .exchange();

        responseSpec
                .expectStatus()
                .isNotFound();
    }

}





















