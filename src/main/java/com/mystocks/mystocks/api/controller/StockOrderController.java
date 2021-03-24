package com.mystocks.mystocks.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mystocks.mystocks.api.dto.OrderDto;
import com.mystocks.mystocks.domain.AccountService;
import com.mystocks.mystocks.domain.AccountSummary;

@RestController
@RequestMapping(value = "/api/order")
public class StockOrderController {

    private final AccountService accountService;

    public StockOrderController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/buy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountSummary> buy(@RequestBody OrderDto body) {
        var accountSummary = accountService.buy(body.getEquity(), body.getQuantity());
        return ResponseEntity.ok(accountSummary);
    }

    @PostMapping(value = "/sell", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountSummary> sell(@RequestBody OrderDto body) {
        var accountSummary = accountService.sell(body.getEquity(), body.getQuantity());
        return ResponseEntity.ok(accountSummary);
    }
}
