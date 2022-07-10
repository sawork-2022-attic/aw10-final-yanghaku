package com.micropos.carts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;

import java.util.concurrent.ConcurrentHashMap;

@EnableDiscoveryClient
@EnableSpringWebSession
@SpringBootApplication
public class CartsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartsApplication.class, args);
    }

    @Bean
    public ReactiveSessionRepository reactiveSessionRepository() {
        return new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
    }
}
