package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private BigDecimal balance;
    private Map<String, Integer> stockList;

    private Portfolio(BigDecimal initialBalance, Map<String, Integer> stockList) {
        this.balance = initialBalance;
        this.stockList = stockList;
    }

    public static Portfolio open() {
        return new Portfolio(BigDecimal.ZERO, new HashMap<>());
    }

    static Portfolio open(BigDecimal initialBalance) {
        return new Portfolio(initialBalance, new HashMap<>());
    }

    static Portfolio open(BigDecimal initialBalance, Map<String, Integer> stockList) {
        return new Portfolio(initialBalance, stockList);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public PortfolioSummary getSummary() {
        return PortfolioSummary.builder()
            .balance(balance)
            .stockList(stockList)
            .build();
    }

    public Portfolio deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return this;
    }

    public Portfolio withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        return this;
    }

    public Portfolio buy(String stock, int quantity, BigDecimal price) {
        var existingStockQuantity = stockList.getOrDefault(stock, 0);
        stockList.put(stock, quantity + existingStockQuantity);
        balance = balance.subtract(price.multiply(BigDecimal.valueOf(quantity)));
        return this;
    }

    public Portfolio sell(String stock, int quantity, BigDecimal price) {
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
