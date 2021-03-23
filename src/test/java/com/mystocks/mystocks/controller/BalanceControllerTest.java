package com.mystocks.mystocks.controller;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystocks.mystocks.domain.AccountService;
import com.mystocks.mystocks.domain.Randomiser;
import com.mystocks.mystocks.dto.AccountDto;
import com.mystocks.mystocks.dto.BalanceDto;

class BalanceControllerTest {

    private final AccountService accountService = mock(AccountService.class);
    private final BalanceController balanceController = new BalanceController(accountService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(balanceController).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Randomiser randomiser = new Randomiser();

    @Test
    void shouldReturnBalance() throws Exception {
        var balance = randomiser.amount();
        when(accountService.getBalance()).thenReturn(balance);

        var expectedDto = new BalanceDto(String.valueOf(balance));
        mockMvc
            .perform(get("/api/account/balance"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void shouldReturnBalanceAfterDeposit() throws Exception {
        var newBalance = randomiser.amount();
        var depositAmount = randomiser.amount();

        when(accountService.deposit(depositAmount)).thenReturn(newBalance);

        var requestDto = new AccountDto(String.valueOf(depositAmount));
        var expectedDto = new BalanceDto(String.valueOf(newBalance));

        mockMvc
            .perform(post("/api/account/deposit")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void shouldReturnBalanceAfterWithdrawal() throws Exception {
        var newBalance = randomiser.amount();
        var withdrawalAmount = randomiser.amount();

        when(accountService.withdraw(withdrawalAmount)).thenReturn(newBalance);

        var requestDto = new AccountDto(String.valueOf(withdrawalAmount));
        var expectedDto = new BalanceDto(String.valueOf(newBalance));

        mockMvc
            .perform(post("/api/account/withdraw")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }
}