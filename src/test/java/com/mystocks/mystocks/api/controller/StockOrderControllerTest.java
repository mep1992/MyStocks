package com.mystocks.mystocks.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystocks.mystocks.api.dto.BuyOrderDto;
import com.mystocks.mystocks.domain.AccountService;
import com.mystocks.mystocks.domain.Randomiser;

class StockOrderControllerTest {
    private final AccountService accountService = mock(AccountService.class);
    private final StockOrderController stockOrderController = new StockOrderController(accountService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(stockOrderController).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Randomiser randomiser = new Randomiser();

    @Test
    void shouldBuyStock() throws Exception {
        var equity = randomiser.equity();
        var stockQuantity = randomiser.stockQuantity();
        var requestDto = BuyOrderDto.builder()
            .equity(equity)
            .quantity(stockQuantity)
            .build();
        mockMvc
            .perform(post("/api/order/buy")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
        verify(accountService).buy(equity, stockQuantity);
    }
}