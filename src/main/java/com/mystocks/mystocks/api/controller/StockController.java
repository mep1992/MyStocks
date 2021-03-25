package com.mystocks.mystocks.api.controller;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mystocks.mystocks.api.dto.PortfolioSummaryDto;
import com.mystocks.mystocks.api.dto.OrderDto;
import com.mystocks.mystocks.domain.PortfolioService;

@RestController
@RequestMapping(value = "/api")
public class StockController {

    private final PortfolioService portfolioService;

    public StockController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping(value = "/stock/buy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PortfolioSummaryDto> buy(@RequestBody OrderDto body) {
        var portfolioSummary = portfolioService.buyStock(body.getStock(), body.getQuantity());
        return ResponseEntity.ok(PortfolioSummaryDto.from(portfolioSummary));
    }

    @PostMapping(value = "/stock/sell", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PortfolioSummaryDto> sell(@RequestBody OrderDto body) {
        var portfolioSummary = portfolioService.sellStock(body.getStock(), body.getQuantity());
        return ResponseEntity.ok(PortfolioSummaryDto.from(portfolioSummary));
    }

    @GetMapping(value = "/stock/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> list() {
        var stockList = portfolioService.getStockList();
        return ResponseEntity.ok(stockList);
    }
}
