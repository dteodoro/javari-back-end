package com.dteodoro.javari.javariauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"com.dteodoro.javari.javaricore.domain"})
@EnableJpaRepositories({"com.dteodoro.javari.javaricore.repository"})
public class JavariAuthApp {

	public static void main(String[] args) {
		SpringApplication.run(JavariAuthApp.class, args);
	}

}
