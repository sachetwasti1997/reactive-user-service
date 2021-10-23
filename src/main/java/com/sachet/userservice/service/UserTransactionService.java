package com.sachet.userservice.service;

import com.sachet.userservice.constants.TransactionStatus;
import com.sachet.userservice.dto.TransactionResponse;
import com.sachet.userservice.entity.UserTransaction;
import com.sachet.userservice.repository.UserRepository;
import com.sachet.userservice.repository.UserTransactionRepository;
import com.sachet.userservice.util.EntityDtoUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserTransactionService {

    private final UserTransactionRepository userTransactionRepository;
    private final UserRepository userRepository;

    public UserTransactionService(UserTransactionRepository userTransactionRepository, UserRepository userRepository) {
        this.userTransactionRepository = userTransactionRepository;
        this.userRepository = userRepository;
    }

    public Mono<UserTransaction> createTransaction(
            Mono<UserTransaction> transaction
    ){
        return transaction
                .flatMap(transaction1 -> userRepository
                        .updateUserBalance(transaction1.getUserId(), transaction1.getAmount())
                        .filter(Boolean::booleanValue)//if this is true the pipeline will continue otherwise it will be empty
                        .flatMap(aBoolean -> {
                            transaction1.setTransactionStatus(TransactionStatus.APPROVED.name());
                            return userTransactionRepository.save(transaction1);
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                            transaction1.setTransactionStatus(TransactionStatus.DECLINED.name());
                            return userTransactionRepository.save(transaction1);
                        }))
                );
    }

    public Mono<UserTransaction> getTransactionById(Integer id){
        return userTransactionRepository
                .findById(id);
    }

    public Flux<UserTransaction> getUserTransactions(Integer userId){
        return userTransactionRepository
                .getUserTransactionsByUserId(userId);
    }

    public Mono<ResponseEntity<Void>> deleteUserTransactionById(Integer id){
        return userTransactionRepository
                .findById(id)
                .flatMap(transaction -> userTransactionRepository
                        .delete(transaction)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))
                        )
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<Void>> deleteAllTransactionsForUser(Integer userId){
        return userRepository
                .findById(userId)
                .then(
                        userTransactionRepository
                                .deleteUserTransactionsByUserId(userId)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}















