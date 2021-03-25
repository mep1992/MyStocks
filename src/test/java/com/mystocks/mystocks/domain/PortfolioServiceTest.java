package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PortfolioServiceTest {

    private final Randomiser randomiser = new Randomiser();
    private final PricingService pricingService = mock(PricingService.class);
    private final BigDecimal initialBalance = randomiser.amount();
    private final String stock = randomiser.stock();
    private final int quantity = randomiser.stockQuantity();
    private final BigDecimal stockPrice = randomiser.stockPrice();

    @Test
    void ShouldGetPortfolioBalance() {
        var portfolioService = new PortfolioService(Portfolio.open(initialBalance), pricingService);

        assertThat(portfolioService.getBalance(), is(initialBalance));
    }

    @Test
    void ShouldReturnNewBalanceAfterDeposit() {
        var depositAmount = randomiser.amount();
        var portfolioService = new PortfolioService(Portfolio.open(initialBalance), pricingService);
        portfolioService.deposit(depositAmount);

        assertThat(portfolioService.getBalance(), is(initialBalance.add(depositAmount)));
    }

    @Test
    void ShouldReturnNewBalanceAfterWithdrawal() {
        var withdrawalAmount = randomiser.amount();
        var portfolioService = new PortfolioService(Portfolio.open(initialBalance), pricingService);
        portfolioService.withdraw(withdrawalAmount);

        assertThat(portfolioService.getBalance(), is(initialBalance.subtract(withdrawalAmount)));
    }

    @Test
    void shouldAddStockToStockListWhenBuying() {
        when(pricingService.getLastOpenPrice(stock)).thenReturn(stockPrice);
        var portfolioService = new PortfolioService(Portfolio.open(initialBalance), pricingService);

        var portfolioSummary = portfolioService.buyStock(stock, randomiser.stockQuantity());

        assertThat(portfolioSummary.getStockList(), hasKey(stock));
    }

    @Test
    void shouldRemoveStockFromStockListWhenSelling() {
        when(pricingService.getLastOpenPrice(stock)).thenReturn(stockPrice);
        var portfolioWithStocks = Portfolio.open(initialBalance, createMutableMap(Map.of(stock, quantity)));

        var portfolioService = new PortfolioService(portfolioWithStocks, pricingService);
        var portfolioSummary = portfolioService.sellStock(stock, quantity);

        assertThat(portfolioSummary.getStockList(), not(hasKey(stock)));
    }

    @Test
    void shouldGetStockList() {
        var portfolioWithStocks = Portfolio.open(initialBalance, createMutableMap(Map.of(stock, quantity)));

        var portfolioService = new PortfolioService(portfolioWithStocks, pricingService);
        var stockList = portfolioService.getStockList();

        assertThat(stockList, aMapWithSize(1));
        assertThat(stockList, hasEntry(stock, quantity));
    }

    private Map<String, Integer> createMutableMap(Map<String, Integer> immutableMap) {
        return new HashMap<>(immutableMap);
    }
}