package com.sachet.userservice.util;

import com.sachet.userservice.constants.TransactionStatus;
import com.sachet.userservice.dto.TransactionResponse;
import com.sachet.userservice.entity.UserTransaction;

public class EntityDtoUtil {

    public static TransactionResponse toTransactionResponse(
            UserTransaction transaction,
            TransactionStatus status
            ){
        return new TransactionResponse(
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                status
        );
    }

}
