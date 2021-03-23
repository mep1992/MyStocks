package com.mystocks.mystocks.api.controller;

import java.math.BigDecimal;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mystocks.mystocks.domain.AccountService;
import com.mystocks.mystocks.api.dto.AccountDto;
import com.mystocks.mystocks.api.dto.BalanceDto;

@RestController
@RequestMapping(value = "/api/account")
public class BalanceController {

    private final AccountService accountService;

    public BalanceController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> balance() {
        var balance = accountService.getBalance();
        return ResponseEntity.ok(new BalanceDto(balance.toString()));
    }

    @PostMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> deposit(@RequestBody AccountDto body) {
        var newBalance = accountService.deposit(new BigDecimal(body.getAmount()));
        return ResponseEntity.ok(new BalanceDto(newBalance.toString()));
    }

    @PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> withdraw(@RequestBody AccountDto body) {
        var newBalance = accountService.withdraw(new BigDecimal(body.getAmount()));
        return ResponseEntity.ok(new BalanceDto(newBalance.toString()));
    }
}
