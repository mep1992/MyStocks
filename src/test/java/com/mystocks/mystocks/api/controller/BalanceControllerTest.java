package com.mystocks.mystocks.api.controller;

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
import com.mystocks.mystocks.domain.PortfolioService;
import com.mystocks.mystocks.domain.Randomiser;
import com.mystocks.mystocks.api.dto.BalanceDto;

class BalanceControllerTest {

    private final PortfolioService portfolioService = mock(PortfolioService.class);
    private final BalanceController balanceController = new BalanceController(portfolioService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(balanceController).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Randomiser randomiser = new Randomiser();

    @Test
    void shouldReturnBalance() throws Exception {
        var balance = randomiser.amount();
        when(portfolioService.getBalance()).thenReturn(balance);

        var expectedDto = new BalanceDto(String.valueOf(balance));
        mockMvc
            .perform(get("/api/balance/"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void shouldReturnBalanceAfterDeposit() throws Exception {
        var newBalance = randomiser.amount();
        var depositAmount = randomiser.amount();

        when(portfolioService.deposit(depositAmount)).thenReturn(newBalance);

        var requestDto = new BalanceDto(String.valueOf(depositAmount));
        var expectedDto = new BalanceDto(String.valueOf(newBalance));

        mockMvc
            .perform(post("/api/balance/deposit")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void shouldReturnBalanceAfterWithdrawal() throws Exception {
        var newBalance = randomiser.amount();
        var withdrawalAmount = randomiser.amount();

        when(portfolioService.withdraw(withdrawalAmount)).thenReturn(newBalance);

        var requestDto = new BalanceDto(String.valueOf(withdrawalAmount));
        var expectedDto = new BalanceDto(String.valueOf(newBalance));

        mockMvc
            .perform(post("/api/balance/withdraw")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }
}