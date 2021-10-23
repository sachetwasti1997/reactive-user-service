package com.sachet.userservice.service;

import com.sachet.userservice.entity.User;
import com.sachet.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> findAll(){
        return userRepository
                .findAll();
    }

    public Mono<User> findById(Integer id){
        return userRepository
                .findById(id);
    }

    public Mono<User> saveUser(Mono<User>userMono){
        return userMono
                .flatMap(userRepository::save);
    }

    public Mono<User> updateUser(Integer id, Mono<User> userMono){

        return userRepository
                .findById(id)
                .flatMap(user -> {
                    return userMono
                            .flatMap(user1 -> {
                                user.setBalance(user1.getBalance());
                                user.setName(user1.getName());
                                return userRepository.save(user);
                            });
                });

    }

    public Mono<ResponseEntity<Void>> deleteUserById(Integer id){
        return userRepository
                .findById(id)
                .flatMap(user -> userRepository.delete(user).then(Mono.just(new ResponseEntity<>(HttpStatus.OK))));
    }

    public Mono<ResponseEntity<Void>> deleteUser(Mono<User> userMono){
        return userRepository
                .findById(userMono.map(User::getId))
                .flatMap(user -> userRepository.delete(user).then(Mono.just(new ResponseEntity<>(HttpStatus.OK))));
    }

}
















