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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystocks.mystocks.api.dto.PortfolioSummaryDto;
import com.mystocks.mystocks.api.dto.OrderDto;
import com.mystocks.mystocks.domain.PortfolioService;
import com.mystocks.mystocks.domain.PortfolioSummary;
import com.mystocks.mystocks.domain.Randomiser;

class StockControllerTest {
    private final PortfolioService portfolioService = mock(PortfolioService.class);
    private final StockController stockController = new StockController(portfolioService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Randomiser randomiser = new Randomiser();

    private final String equity = randomiser.stock();
    private final int stockQuantity = randomiser.stockQuantity();
    private final OrderDto requestDto = OrderDto.builder()
        .stock(equity)
        .quantity(stockQuantity)
        .build();
    private final PortfolioSummary portfolioSummary = new PortfolioSummary(randomiser.amount(), Map.of(equity, stockQuantity));

    @Test
    void shouldBuyStock() throws Exception {
        when(portfolioService.buyStock(equity, stockQuantity)).thenReturn(portfolioSummary);

        var expectedDto = PortfolioSummaryDto.from(portfolioSummary);
        mockMvc
            .perform(post("/api/stock/buy")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));

    }

    @Test
    void shouldSellStock() throws Exception {
        when(portfolioService.sellStock(equity, stockQuantity)).thenReturn(portfolioSummary);

        var expectedDto = PortfolioSummaryDto.from(portfolioSummary);
        mockMvc
            .perform(post("/api/stock/sell")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void shouldGetStockList() throws Exception {
        var stockList = Map.of("GOOG", randomiser.stockQuantity(), "IBM", randomiser.stockQuantity());
        when(portfolioService.getStockList()).thenReturn(stockList);

        mockMvc
            .perform(get("/api/stock/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(stockList)));
    }
}