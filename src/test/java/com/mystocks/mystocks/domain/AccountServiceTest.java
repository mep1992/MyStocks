package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private final Randomiser randomiser = new Randomiser();
    private final PricingService pricingService = mock(PricingService.class);
    private final BigDecimal initialBalance = randomiser.amount();
    private final String stock = randomiser.stock();
    private final int quantity = randomiser.stockQuantity();
    private final BigDecimal stockPrice = randomiser.stockPrice();

    @Test
    void ShouldGetAccountBalance() {
        var accountService = new AccountService(Account.open(initialBalance), pricingService);

        assertThat(accountService.getBalance(), is(initialBalance));
    }

    @Test
    void ShouldReturnNewBalanceAfterDeposit() {
        var depositAmount = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance), pricingService);
        accountService.deposit(depositAmount);

        assertThat(accountService.getBalance(), is(initialBalance.add(depositAmount)));
    }

    @Test
    void ShouldReturnNewBalanceAfterWithdrawal() {
        var withdrawalAmount = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance), pricingService);
        accountService.withdraw(withdrawalAmount);

        assertThat(accountService.getBalance(), is(initialBalance.subtract(withdrawalAmount)));
    }

    @Test
    void shouldAddStockToStockListWhenBuying() {
        when(pricingService.getLastOpenPrice(stock)).thenReturn(stockPrice);
        var accountService = new AccountService(Account.open(initialBalance), pricingService);

        var accountSummary = accountService.buy(stock, randomiser.stockQuantity());

        assertThat(accountSummary.getStockList(), hasKey(stock));
    }

    @Test
    void shouldRemoveStockFromStockListWhenSelling() {
        when(pricingService.getLastOpenPrice(stock)).thenReturn(stockPrice);
        var accountWithStocks = Account.open(initialBalance, createMutableMap(Map.of(stock, quantity)));

        var accountService = new AccountService(accountWithStocks, pricingService);
        var accountSummary = accountService.sell(stock, quantity);

        assertThat(accountSummary.getStockList(), not(hasKey(stock)));
    }

    private Map<String, Integer> createMutableMap(Map<String, Integer> immutableMap) {
        return new HashMap<>(immutableMap);
    }
}