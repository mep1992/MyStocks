package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private final Portfolio portfolio;
    private final PricingService pricingService;

    public PortfolioService(Portfolio portfolio, PricingService pricingService) {
        this.portfolio = portfolio;
        this.pricingService = pricingService;
    }

    public BigDecimal getBalance() {
        return portfolio.getBalance();
    }

    public BigDecimal deposit(BigDecimal amount) {
        return portfolio.deposit(amount).getBalance();
    }

    public BigDecimal withdraw(BigDecimal amount) {
        return portfolio.withdraw(amount).getBalance();
    }

    public PortfolioSummary buyStock(String stock, int stockQuantity) {
        return portfolio
            .buy(stock, stockQuantity, pricingService.getLastOpenPrice(stock))
            .getSummary();
    }

    public PortfolioSummary sellStock(String stock, int stockQuantity) {
        return portfolio
            .sell(stock, stockQuantity, pricingService.getLastOpenPrice(stock))
            .getSummary();
    }

    public Map<String, Integer> getStockList() {
        return portfolio.getSummary().getStockList();
    }
}
