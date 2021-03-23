package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AccountServiceTest {

    private final Randomiser randomiser = new Randomiser();

    @Test
    void ShouldGetAccountBalance() {
        var initialBalance = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance));

        assertThat(accountService.getBalance(), is(initialBalance));
    }

    @Test
    void ShouldReturnNewBalanceAfterDeposit() {
        var initialBalance = randomiser.amount();
        var depositAmount = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance));
        accountService.deposit(depositAmount);

        assertThat(accountService.getBalance(), is(initialBalance.add(depositAmount)));
    }

    @Test
    void ShouldReturnNewBalanceAfterWithdrawal() {
        var initialBalance = randomiser.amount();
        var withdrawalAmount = randomiser.amount();
        var accountService = new AccountService(Account.open(initialBalance));
        accountService.withdraw(withdrawalAmount);

        assertThat(accountService.getBalance(), is(initialBalance.subtract(withdrawalAmount)));
    }
}