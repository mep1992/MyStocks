package com.mystocks.mystocks.domain;

public interface PricingService {
    EquityPrice getLastOpenPrice(String equity);
}
