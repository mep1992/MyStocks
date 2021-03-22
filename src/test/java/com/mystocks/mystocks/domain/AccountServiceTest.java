package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AccountServiceTest {

    private final AccountService accountService = new AccountService();

    @Test
    void ShouldGetAccountBalance() {
        assertThat(accountService.getBalance(), is(BigDecimal.ZERO));
    }
}