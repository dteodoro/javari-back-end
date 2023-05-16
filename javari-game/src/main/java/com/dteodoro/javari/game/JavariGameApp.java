package com.dteodoro.javari.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"com.dteodoro.javari.core.domain"})
@EnableJpaRepositories({"com.dteodoro.javari.core.repository"})
@EnableFeignClients
public class JavariGameApp {

	public static void main(String[] args) {
		SpringApplication.run(JavariGameApp.class, args);
	}

}
