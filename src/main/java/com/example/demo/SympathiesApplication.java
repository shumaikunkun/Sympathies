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
			userRepo.save(new User("test@example.com", "楽天太郎", "passwd", 100));
			userRepo.save(new User("test2@example.com", "楽天花子", "mypasswd", 50));
			userRepo.save(new User("shira@gmail.com", "シラトリ ユウジ", "shira", 100));
			userRepo.save(new User("shuma@gmail.com", "清水秀馬", "shuma", 1000));
			userRepo.save(new User("inoue@gmail.com", "井上将斗", "inoue", 100));
			userRepo.save(new User("hirata@gmail.com", "平田雄也", "hirata", 100));
			userRepo.save(new User("yanagido@gmail.com", "柳戸新一", "yamnagido", 100));
			userRepo.save(new User("rakuten@gmail.com", "ラック・テン", "rakuten", 100));

		};
	}

	@Bean
	public CommandLineRunner demoGoods(GoodsRepository repository) {
		goodsRepo = repository;
		return (args) -> {
			// save a couple of users
			goodsRepo.save(new Goods(1L, "解析学IIの過去問", "2015年の期末試験の過去問です．", 10, "images/document_regulation.png"));
			goodsRepo.save(new Goods(2L, "数学の解き方", "微分積分問題集の解法です．", 20, "images/math.png"));
			goodsRepo.save(new Goods(3L, "英語 ドキュメント", "専門英語の参考文献です．", 40, "images/english.png"));
			goodsRepo.save(new Goods(4L, "データベース概論板書", "商品説明です．", 10, "images/1.jpg"));
			goodsRepo.save(new Goods(5L, "プログラミング概論解答", "商品説明です．", 20, "images/2.jpg"));
			goodsRepo.save(new Goods(6L, "プロ演２課題", "商品説明です．", 30, "images/3.jpg"));
			goodsRepo.save(new Goods(7L, "過去問答え", "商品説明です．", 40, "images/4.jpg"));
			goodsRepo.save(new Goods(8L, "アプリ案", "ヒミツ", 100, "images/5.jpg"));

			for (Goods item : goodsRepo.findAll()) {
				log.info(item.toString());
			}
		};
	}

	@Bean
	public CommandLineRunner demoTransaction(TransactionRepository repository) {
		transactionRepo = repository;
		return (args) -> {
			// save a couple of users
			transactionRepo.save(new Transaction(1L, 2L, 4L));
		};
	}

}
