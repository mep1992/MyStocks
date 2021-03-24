package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountSummary {
    private final BigDecimal balance;
    private final Map<String, Integer> stockList;
}
