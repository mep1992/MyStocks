package com.mystocks.mystocks.domain;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
