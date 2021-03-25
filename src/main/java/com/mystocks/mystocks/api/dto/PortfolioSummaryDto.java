package com.mystocks.mystocks.api.dto;

import java.util.Map;
import com.mystocks.mystocks.domain.PortfolioSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PortfolioSummaryDto {
    private final String balance;
    private final Map<String, Integer> stockList;

    public static PortfolioSummaryDto from(PortfolioSummary portfolioSummary) {
        return new PortfolioSummaryDto(portfolioSummary.getBalance().toString(), portfolioSummary.getStockList());
    }
}
