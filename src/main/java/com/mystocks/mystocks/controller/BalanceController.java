package com.mystocks.mystocks.controller;

import java.math.BigDecimal;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mystocks.mystocks.domain.AccountService;
import com.mystocks.mystocks.dto.BalanceDto;

@RestController
@RequestMapping(value = "/api/account")
public class BalanceController {

    private final AccountService accountService;

    public BalanceController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> getBalance() {
        var balance = accountService.getBalance();
        return ResponseEntity.ok(BalanceDto.builder().amount(balance.toString()).build());
    }
}
