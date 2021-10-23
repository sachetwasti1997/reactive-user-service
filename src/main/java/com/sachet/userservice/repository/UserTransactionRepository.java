package com.sachet.userservice.repository;

import com.sachet.userservice.entity.UserTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Integer> {

    Flux<UserTransaction> getUserTransactionsByUserId(Integer userId);

    Mono<Void> deleteUserTransactionsByUserId(Integer userId);

}
