package com.dteodoro.javari.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"com.dteodoro.javari.core.domain"})
@EnableJpaRepositories({"com.dteodoro.javari.core.repository"})
public class JavariAuthApp {

	public static void main(String[] args) {
		SpringApplication.run(JavariAuthApp.class, args);
	}

}
