package com.mystocks.mystocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.mystocks.mystocks.domain.Portfolio;

@SpringBootApplication
public class MystocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystocksApplication.class, args);
    }

    @Bean
    public Portfolio createDefaultPortfolio() {
        return Portfolio.open();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
