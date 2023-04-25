package com.sweethome.payment.service.impl;

import com.sweethome.payment.feign.PaymentServiceClient;
import com.sweethome.payment.model.TransactionDetailsEntity;
import com.sweethome.payment.repository.TransactionRepository;
import com.sweethome.payment.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentServiceClient paymentServiceClient;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public int createTransaction(TransactionDetailsEntity transactionDetailsEntity) {
        TransactionDetailsEntity savedTransaction = transactionRepository.save(transactionDetailsEntity);
        return savedTransaction.getTransactionId();
    }

    @Override
    public TransactionDetailsEntity getTransaction(int transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }
}
