package com.mystocks.mystocks.domain;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private final Randomiser randomiser = new Randomiser();
    private PricingService pricingService = mock(PricingService.class);

    @Test
    void ShouldGetAccountBalance() {
        var initialBalance = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance), pricingService);

        assertThat(accountService.getBalance(), is(initialBalance));
    }

    @Test
    void ShouldReturnNewBalanceAfterDeposit() {
        var initialBalance = randomiser.amount();
        var depositAmount = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance), pricingService);
        accountService.deposit(depositAmount);

        assertThat(accountService.getBalance(), is(initialBalance.add(depositAmount)));
    }

    @Test
    void ShouldReturnNewBalanceAfterWithdrawal() {
        var initialBalance = randomiser.amount();
        var withdrawalAmount = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance), pricingService);
        accountService.withdraw(withdrawalAmount);

        assertThat(accountService.getBalance(), is(initialBalance.subtract(withdrawalAmount)));
    }

    @Test
    void ShouldAddStockToStocksList() {
        var initialBalance = randomiser.amount();
        String equity = randomiser.equity();
        var stockPrice = randomiser.stockPrice();
        when(pricingService.getLastOpenPrice(equity)).thenReturn(stockPrice);
        var accountService = new AccountService(Account.open(initialBalance), pricingService);

        var accountSummary = accountService.buy(equity, randomiser.stockQuantity());

        assertThat(accountSummary.getStockList(), hasKey(equity));
    }
}