package com.cct.stockmarket.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cct.stockmarket.api.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
