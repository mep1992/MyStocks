package com.mystocks.mystocks;

import java.math.BigDecimal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.mystocks.mystocks.domain.Account;

@SpringBootApplication
public class MystocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystocksApplication.class, args);
    }

    @Bean
    public Account createDefaultAccount() {
        return Account.open(BigDecimal.ZERO);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
