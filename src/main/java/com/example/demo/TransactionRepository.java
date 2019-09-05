package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByDestinationUserIdAndGoodsId(Long destinationUserId, Long goodsId);
    List<Transaction> findById(String id);
    List<Transaction> findAll();
}
