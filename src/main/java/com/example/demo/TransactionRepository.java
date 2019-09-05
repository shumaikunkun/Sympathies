package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByDestinationUserId(Long id);
    List<Transaction> findAll();
}
