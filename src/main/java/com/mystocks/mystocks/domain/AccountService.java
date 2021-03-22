package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final Account account;

    public AccountService() {
        this.account = new Account();
    }

    public BigDecimal getBalance() {
        return this.account.getBalance();
    }
}
