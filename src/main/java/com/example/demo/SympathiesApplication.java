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
			goodsRepo.save(new Goods(1L, "過去問", "20XX年の期末試験の過去問です．", 10, "images/document_regulation.png"));
			goodsRepo.save(new Goods(2L, "数学の解き方", "微分積分問題集の解法です．", 20, "images/math.png"));
			goodsRepo.save(new Goods(3L, "英語 ドキュメント", "英語の参考文献です．", 40, "images/english.png"));
			goodsRepo.save(new Goods(4L, "Test Item 01", "商品説明です．", 10, "images/noimage.png"));
			goodsRepo.save(new Goods(5L, "Test Item 02", "商品説明です．", 20, "images/noimage.png"));
			goodsRepo.save(new Goods(6L, "Test Item 03", "商品説明です．", 30, "images/noimage.png"));
			goodsRepo.save(new Goods(7L, "Test Item 04", "商品説明です．", 40, "images/noimage.png"));
			goodsRepo.save(new Goods(8L, "Test Item 05", "商品説明です．", 100, "images/noimage.png"));

			for (Goods item : goodsRepo.findAll()) {
				log.info(item.toString());
			}
		};
	}
}
