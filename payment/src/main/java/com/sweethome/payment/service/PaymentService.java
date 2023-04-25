package com.sweethome.payment.service;

import com.sweethome.payment.model.TransactionDetailsEntity;

public interface PaymentService {
    int createTransaction(TransactionDetailsEntity transactionDetailsEntity);

    TransactionDetailsEntity getTransaction(int transactionId);
}
