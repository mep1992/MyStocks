package com.mystocks.mystocks.api.controller;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystocks.mystocks.api.dto.AccountSummaryDto;
import com.mystocks.mystocks.api.dto.OrderDto;
import com.mystocks.mystocks.domain.AccountService;
import com.mystocks.mystocks.domain.AccountSummary;
import com.mystocks.mystocks.domain.Randomiser;

class StockOrderControllerTest {
    private final AccountService accountService = mock(AccountService.class);
    private final StockOrderController stockOrderController = new StockOrderController(accountService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(stockOrderController).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Randomiser randomiser = new Randomiser();

    private final String equity = randomiser.stock();
    private final int stockQuantity = randomiser.stockQuantity();
    private final OrderDto requestDto = OrderDto.builder()
        .equity(equity)
        .quantity(stockQuantity)
        .build();
    private final AccountSummary accountSummary = new AccountSummary(randomiser.amount(), Map.of(equity, stockQuantity));

    @Test
    void shouldBuyStock() throws Exception {
        when(accountService.buy(equity, stockQuantity)).thenReturn(accountSummary);

        var expectedDto = AccountSummaryDto.from(accountSummary);
        mockMvc
            .perform(post("/api/order/buy")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));

    }

    @Test
    void shouldSellStock() throws Exception {
        when(accountService.sell(equity, stockQuantity)).thenReturn(accountSummary);

        var expectedDto = AccountSummaryDto.from(accountSummary);
        mockMvc
            .perform(post("/api/order/sell")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }
}