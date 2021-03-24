package com.mystocks.mystocks.domain;

import java.math.BigDecimal;

public interface PricingService {
    BigDecimal getLastOpenPrice(String equity);
}
