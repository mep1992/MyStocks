package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private BigDecimal balance;
    private Map<String, Integer> stockList;

    private Account(BigDecimal initialBalance, Map<String, Integer> stockList) {
        this.balance = initialBalance;
        this.stockList = stockList;
    }

    public static Account open() {
        return new Account(BigDecimal.ZERO, new HashMap<>());
    }

    static Account open(BigDecimal initialBalance) {
        return new Account(initialBalance, new HashMap<>());
    }

    static Account open(BigDecimal initialBalance, Map<String, Integer> stockList) {
        return new Account(initialBalance, stockList);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountSummary getSummary() {
        return AccountSummary.builder()
            .balance(balance)
            .stockList(stockList)
            .build();
    }

    public Account deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return this;
    }

    public Account withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        return this;
    }

    public Account buy(String stock, int quantity, BigDecimal price) {
        var existingStockQuantity = stockList.getOrDefault(stock, 0);
        stockList.put(stock, quantity + existingStockQuantity);
        balance = balance.subtract(price.multiply(BigDecimal.valueOf(quantity)));
        return this;
    }

    public Account sell(String stock, int quantity, BigDecimal price) {
        var newStockQuantity = stockList.getOrDefault(stock, 0) - quantity;
        if(newStockQuantity == 0) {
            stockList.remove(stock);
        } else {
            stockList.put(stock, newStockQuantity);
        }
        balance = balance.add(price.multiply(BigDecimal.valueOf(quantity)));
        return this;
    }
}
