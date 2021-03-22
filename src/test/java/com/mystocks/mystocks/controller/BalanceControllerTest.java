package com.mystocks.mystocks.controller;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystocks.mystocks.domain.AccountService;
import com.mystocks.mystocks.dto.BalanceDto;

class BalanceControllerTest {

    private final AccountService accountService = mock(AccountService.class);
    private final BalanceController balanceController = new BalanceController(accountService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(balanceController).build();;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnBalance() throws Exception {
        var balance = BigDecimal.valueOf(50);
        when(accountService.getBalance()).thenReturn(balance);

        var expectedDto = BalanceDto.builder()
            .amount(String.valueOf(balance))
            .build();
        mockMvc
            .perform(get("/api/account/balance"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }
}