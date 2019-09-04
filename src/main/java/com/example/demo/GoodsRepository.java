package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Long> {

    List<Goods> findById(String id);
    List<Goods> findAll();
}
