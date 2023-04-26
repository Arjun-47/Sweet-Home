package com.sweethome.payment.controller;

import com.sweethome.payment.constants.PaymentMode;
import com.sweethome.payment.dto.TransactionDTO;
import com.sweethome.payment.model.TransactionDetailsEntity;
import com.sweethome.payment.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/payment")
public class TransactionServiceController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    ModelMapper modelMapper;


    @RequestMapping(value = "/transaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createTransaction(@RequestBody TransactionDTO transactionDTO) {

        boolean isValidRequest;
        if(transactionDTO.getPaymentMode().equalsIgnoreCase(PaymentMode.UPI)){
            isValidRequest = !(Objects.isNull(transactionDTO.getUpiId()) || transactionDTO.getUpiId().isEmpty());
            transactionDTO.setCardNumber(null);
        } else if(transactionDTO.getPaymentMode().equalsIgnoreCase(PaymentMode.CARD)){
            isValidRequest = !(Objects.isNull(transactionDTO.getCardNumber()) || transactionDTO.getCardNumber().isEmpty());
            transactionDTO.setUpiId(null);
        } else{
            isValidRequest = false;
        }
        if(isValidRequest){
            TransactionDetailsEntity transactionDetailsEntity = modelMapper.map(transactionDTO, TransactionDetailsEntity.class);
            return new ResponseEntity<>(paymentService.createTransaction(transactionDetailsEntity), HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<TransactionDetailsEntity> getTransaction(@PathVariable(name = "transactionId") int transactionId) {
        TransactionDetailsEntity transactionDetailsEntity = paymentService.getTransaction(transactionId);
        return Objects.isNull(transactionDetailsEntity) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(transactionDetailsEntity, HttpStatus.OK);
    }
}
