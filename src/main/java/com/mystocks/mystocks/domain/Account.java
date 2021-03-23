package com.mystocks.mystocks.domain;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;

    private Account(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    public static Account open(BigDecimal initialBalance) {
        return new Account(initialBalance);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return this;
    }

    public Account withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        return this;
    }
}
