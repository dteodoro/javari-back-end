package com.dteodoro.javari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EspnConnectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EspnConnectionApplication.class, args);
	}
}
