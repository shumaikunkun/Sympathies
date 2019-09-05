package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SympathiesApplication {

	private static final Logger log = LoggerFactory.getLogger(SympathiesApplication.class);

	UserRepository userRepo;
	GoodsRepository goodsRepo;
	TransactionRepository transactionRepo;

	public static void main(String[] args) {
		SpringApplication.run(SympathiesApplication.class);
	}

	@Bean
	public CommandLineRunner demoUser(UserRepository repository) {
		userRepo = repository;
		return (args) -> {
			// save a couple of users
			userRepo.save(new User("test@example.com", "Taro", "passwd", 100));
			userRepo.save(new User("test2@example.com", "Hanako", "mypasswd", 50));
		};
	}

	@Bean
	public CommandLineRunner demoGoods(GoodsRepository repository) {
		goodsRepo = repository;
		return (args) -> {
			// save a couple of users
			goodsRepo.save(new Goods(1L, "Assignment", "Math", 10, "images/document_regulation.png"));
			goodsRepo.save(new Goods(2L, "Assignment2", "English", 10, "file"));
		};
	}

	@Bean
	public CommandLineRunner demoTransaction(TransactionRepository repository) {
		transactionRepo = repository;
		return (args) -> {
			// save a couple of users
			transactionRepo.save(new Transaction(1L, 2L, 3L));
		};
	}

}
