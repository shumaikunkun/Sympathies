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

	public static void main(String[] args) {
		SpringApplication.run(SympathiesApplication.class);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			// save a couple of users
			repository.save(new User("test@example.com", "Taro", "passwd", 100));
			repository.save(new User("test2@example.com", "Hanako", "mypasswd", 50));

			// fetch all users
			log.info("Users found with findAll():");
			log.info("---------------------------");
			for (User user : repository.findAll()) {
				log.info(user.toString());
			}
			log.info("");

			// fetch an individual user by ID
			List<User> user = repository.findByMailAndPassward("test@example.com", "passwd");
			if (user == null || user.size() == 0) {
				log.info("FALSE");
			} else {
				log.info("TRUE");
			}
		};
	}
}
