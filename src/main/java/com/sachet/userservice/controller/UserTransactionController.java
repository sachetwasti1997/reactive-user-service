package com.sachet.userservice.controller;

import com.sachet.userservice.entity.UserTransaction;
import com.sachet.userservice.service.UserTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user_transaction")
public class UserTransactionController {

    private final UserTransactionService userTransactionService;

    public UserTransactionController(UserTransactionService userTransactionService) {
        this.userTransactionService = userTransactionService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<UserTransaction>> createUserTransaction(@RequestBody Mono<UserTransaction> userTransactionMono){
        return userTransactionService
                .createTransaction(userTransactionMono)
                .map(transaction -> ResponseEntity
                        .ok()
                        .body(transaction));
    }

}
















