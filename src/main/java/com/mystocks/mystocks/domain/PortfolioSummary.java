package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PortfolioSummary {
    private final BigDecimal balance;
    private final Map<String, Integer> stockList;
}
