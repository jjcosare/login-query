package com.jjcosare.isr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class LoginQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginQueryApplication.class, args);
	}
}
