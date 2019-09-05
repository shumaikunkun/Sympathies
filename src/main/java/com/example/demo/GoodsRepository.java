package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Long> {

    List<Goods> findByUserId(Long id);
    List<Goods> findAll();
}
