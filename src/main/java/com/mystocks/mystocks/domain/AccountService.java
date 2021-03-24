package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final Account account;
    private final PricingService pricingService;

    public AccountService(Account account, PricingService pricingService) {
        this.account = account;
        this.pricingService = pricingService;
    }

    public BigDecimal getBalance() {
        return account.getBalance();
    }

    public BigDecimal deposit(BigDecimal amount) {
        return account.deposit(amount).getBalance();
    }

    public BigDecimal withdraw(BigDecimal amount) {
        return account.withdraw(amount).getBalance();
    }

    public AccountSummary buy(String stock, int stockQuantity) {
        return account
            .buy(stock, stockQuantity, pricingService.getLastOpenPrice(stock))
            .getSummary();
    }

    public AccountSummary sell(String stock, int stockQuantity) {
        return account
            .sell(stock, stockQuantity, pricingService.getLastOpenPrice(stock))
            .getSummary();
    }
}
