package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class PortfolioTest {

    private final Randomiser randomiser = new Randomiser();
    @Test
    void shouldGetBalance() {
        var initialBalance = randomiser.amount();
        var portfolio = Portfolio.open(initialBalance);
        assertThat(portfolio.getBalance(), is(initialBalance));
    }

    @Test
    void shouldAddStockToStockListWhenBuyingAndUpdateBalance() {
        var initialBalance = randomiser.amount(100, 200);
        var portfolio = Portfolio.open(initialBalance);
        var quantity = 3;
        var stockPrice = BigDecimal.valueOf(2.76);
        var stock = randomiser.stock();

        portfolio.buy(stock, quantity, stockPrice);
        var summary = portfolio.getSummary();

        var expectedNewBalance = initialBalance.subtract(stockPrice.multiply(BigDecimal.valueOf(quantity)));
        assertThat(summary.getBalance(), is(expectedNewBalance));
        assertThat(summary.getStockList().entrySet(), hasSize(1));
        assertThat(summary.getStockList(), hasEntry(stock, quantity));
    }

    @Test
    void shouldUpdateStockQuantityForAlreadyPurchasedStockWhenBuying() {
        var portfolio = Portfolio.open(randomiser.amount());
        var stockPrice = BigDecimal.valueOf(2.76);
        var stock = randomiser.stock();

        portfolio.buy(stock, 3, stockPrice);
        portfolio.buy(stock, 7, stockPrice);

        var summary = portfolio.getSummary();
        assertThat(summary.getStockList().entrySet(), hasSize(1));
        assertThat(summary.getStockList(), hasEntry(stock, 10));
    }

    @Test
    void shouldRemoveStockFromStockListWhenSellingAndUpdateBalance() {
        var initialBalance = randomiser.amount(100, 200);
        var quantity = 3;
        var stockPrice = BigDecimal.valueOf(2.76);
        var stock = randomiser.stock();
        var portfolio = Portfolio.open(initialBalance, createMutableMap(Map.of(stock, quantity)));

        portfolio.sell(stock, quantity, stockPrice);
        var summary = portfolio.getSummary();

        var expectedNewBalance = initialBalance.add(stockPrice.multiply(BigDecimal.valueOf(quantity)));
        assertThat(summary.getBalance(), is(expectedNewBalance));
        assertThat(summary.getStockList().entrySet(), empty());
    }

    @Test
    void shouldUpdateStockQuantityForAlreadyPurchasedStockWhenSelling() {
        var stockPrice = BigDecimal.valueOf(2.76);
        var stock = randomiser.stock();
        var portfolio = Portfolio.open(randomiser.amount(), createMutableMap(Map.of(stock, 10)));

        portfolio.sell(stock, 7, stockPrice);

        var summary = portfolio.getSummary();
        assertThat(summary.getStockList().entrySet(), hasSize(1));
        assertThat(summary.getStockList(), hasEntry(stock, 3));
    }

    @Test
    void shouldRemoveStockFromStockListWhenSellingLastStock() {
        var stockPrice = BigDecimal.valueOf(2.76);
        var stock = randomiser.stock();
        var quantity = randomiser.stockQuantity();
        var portfolio = Portfolio.open(randomiser.amount(), createMutableMap(Map.of(stock, quantity)));

        portfolio.sell(stock, quantity, stockPrice);

        var summary = portfolio.getSummary();
        assertThat(summary.getStockList().entrySet(), empty());
    }

    private Map<String, Integer> createMutableMap(Map<String, Integer> immutableMap) {
        return new HashMap<>(immutableMap);
    }
}