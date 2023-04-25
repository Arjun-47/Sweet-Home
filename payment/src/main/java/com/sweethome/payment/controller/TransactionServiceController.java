package com.sweethome.payment.controller;

import com.sweethome.payment.dto.TransactionDTO;
import com.sweethome.payment.model.TransactionDetailsEntity;
import com.sweethome.payment.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class TransactionServiceController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    ModelMapper modelMapper;


    @RequestMapping(value = "/transaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDetailsEntity transactionDetailsEntity = modelMapper.map(transactionDTO, TransactionDetailsEntity.class);
        return ResponseEntity.status(201).body(paymentService.createTransaction(transactionDetailsEntity));
    }


    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<TransactionDetailsEntity> getTransaction(@PathVariable(name = "transactionId") int transactionId) {
        return ResponseEntity.ok(paymentService.getTransaction(transactionId));
    }
}
