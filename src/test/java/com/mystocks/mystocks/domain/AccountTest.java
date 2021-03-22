package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class AccountTest {
    private final Account account = new Account();

    @Test
    void shouldHaveBalanceOfZeroForNewAccount() {
        assertThat(account.getBalance(), is(BigDecimal.ZERO));
    }
}