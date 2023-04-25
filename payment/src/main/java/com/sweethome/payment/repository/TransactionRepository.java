package com.sweethome.payment.repository;

import com.sweethome.payment.model.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionDetailsEntity, Integer> {

}
