package com.sachet.userservice.dto;

import com.sachet.userservice.constants.TransactionStatus;

import java.time.LocalDateTime;

public class TransactionResponse {

    private Integer userId;
    private Double amount;
    private LocalDateTime localDateTime;
    private TransactionStatus transactionStatus;

    public TransactionResponse(Integer userId, Double amount,
                               LocalDateTime localDateTime, TransactionStatus transactionStatus) {
        this.userId = userId;
        this.amount = amount;
        this.localDateTime = localDateTime;
        this.transactionStatus = transactionStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


}
