package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class AccountTest {

    private final Randomiser randomiser = new Randomiser();
    @Test
    void shouldGetBalance() {
        var initialBalance = randomiser.amount();
        var account = Account.open(initialBalance);
        assertThat(account.getBalance(), is(initialBalance));
    }

    @Test
    void shouldAddStockToStockListWhenBuyingAndUpdateBalance() {
        var initialBalance = randomiser.amount(100, 200);
        var account = Account.open(initialBalance);
        var quantity = 3;
        var stockPrice = BigDecimal.valueOf(2.76);
        var equity = randomiser.equity();

        account.buy(equity, quantity, stockPrice);
        var summary = account.getSummary();

        var expectedNewBalance = initialBalance.subtract(stockPrice.multiply(BigDecimal.valueOf(quantity)));
        assertThat(summary.getBalance(), is(expectedNewBalance));
        assertThat(summary.getStockList().entrySet(), hasSize(1));
        assertThat(summary.getStockList(), hasEntry(equity, quantity));
    }

    @Test
    void shouldUpdateStockQuantityForAlreadyPurchasedStock() {
        var account = Account.open(randomiser.amount());
        var stockPrice = BigDecimal.valueOf(2.76);
        var equity = randomiser.equity();

        account.buy(equity, 3, stockPrice);
        account.buy(equity, 7, stockPrice);

        var summary = account.getSummary();
        assertThat(summary.getStockList().entrySet(), hasSize(1));
        assertThat(summary.getStockList(), hasEntry(equity, 10));
    }
}