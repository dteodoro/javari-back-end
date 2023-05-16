package com.dteodoro.javari.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JavariGatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(JavariGatewayApp.class, args);
    }

}
