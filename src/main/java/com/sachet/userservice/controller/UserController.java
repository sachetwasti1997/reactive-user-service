package com.sachet.userservice.controller;

import com.sachet.userservice.entity.User;
import com.sachet.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Integer id){
        return userService
                .findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public Flux<User> findAllUsers(){
        return userService
                .findAll();
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<User>> saveUser(@Valid @RequestBody Mono<User> userMono){
        return userService
                .saveUser(userMono)
                .map(user -> ResponseEntity.ok().body(user));
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable Integer id, @RequestBody Mono<User> userMono){
        return userService
                .updateUser(id, userMono)
                .map(user -> ResponseEntity.ok().body(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    public Mono<ResponseEntity<Void>> deleteUser(@RequestBody Mono<User> userMono){
        return userService
                .deleteUser(userMono)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Integer id){
        return userService
                .deleteUserById(id)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}


















