package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Randomiser {
    public BigDecimal amount() {
        var min = 1;
        var max = 1000;
        var random = BigDecimal.valueOf(min + Math.random() * (max - min));
        return random.setScale(2, RoundingMode.UP);
    }
}
