package com.mystocks.mystocks.api.dto;

import java.math.BigDecimal;
import java.util.Map;
import com.mystocks.mystocks.domain.AccountSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AccountSummaryDto {
    private final String balance;
    private final Map<String, Integer> stockList;

    public static AccountSummaryDto from(AccountSummary accountSummary) {
        return new AccountSummaryDto(accountSummary.getBalance().toString(), accountSummary.getStockList());
    }
}
