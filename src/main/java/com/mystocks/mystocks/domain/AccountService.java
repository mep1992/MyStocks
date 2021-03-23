package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final Account account;

    public AccountService(Account account) {
        this.account = account;
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
}
