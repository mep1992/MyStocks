package com.mystocks.mystocks.api.controller;

import java.math.BigDecimal;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mystocks.mystocks.domain.PortfolioService;
import com.mystocks.mystocks.api.dto.BalanceDto;

@RestController
@RequestMapping(value = "/api")
public class BalanceController {

    private final PortfolioService portfolioService;

    public BalanceController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> balance() {
        var balance = portfolioService.getBalance();
        return ResponseEntity.ok(new BalanceDto(balance.toString()));
    }

    @PostMapping(value = "/balance/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> deposit(@RequestBody BalanceDto body) {
        var newBalance = portfolioService.deposit(new BigDecimal(body.getAmount()));
        return ResponseEntity.ok(new BalanceDto(newBalance.toString()));
    }

    @PostMapping(value = "/balance/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> withdraw(@RequestBody BalanceDto body) {
        var newBalance = portfolioService.withdraw(new BigDecimal(body.getAmount()));
        return ResponseEntity.ok(new BalanceDto(newBalance.toString()));
    }
}
