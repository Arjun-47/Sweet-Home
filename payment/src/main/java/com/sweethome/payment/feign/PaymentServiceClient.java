package com.sweethome.payment.feign;

import com.sweethome.payment.dto.TransactionDTO;
import com.sweethome.payment.model.TransactionDetailsEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "payment-service", url = "${payment-service-url}")
public interface PaymentServiceClient {
    @RequestMapping(value = "/payment/transaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Integer createTransaction(@RequestBody TransactionDTO transactionDTO);


    @RequestMapping(value = "/payment/transaction/{transactionId}", method = RequestMethod.GET)
    TransactionDetailsEntity getTransaction(@PathVariable(name = "transactionId") int transactionId);
}
