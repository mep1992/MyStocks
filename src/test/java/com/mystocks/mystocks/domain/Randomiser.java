package com.mystocks.mystocks.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Randomiser {
    private Random random = new Random();

    public BigDecimal amount() {
        var min = 1;
        var max = 1000;
        var random = BigDecimal.valueOf(min + Math.random() * (max - min));
        return random.setScale(2, RoundingMode.UP);
    }

    public BigDecimal amount(int min, int max) {
        var random = BigDecimal.valueOf(min + Math.random() * (max - min));
        return random.setScale(2, RoundingMode.UP);
    }

    public BigDecimal stockPrice() {
        return amount();
    }

    public String equity() {
        var equities = new String[] { "TEAM", "GOOG", "IBM" };
        return equities[random.nextInt(equities.length)];
    }

    public int stockQuantity() {
        return random.nextInt(10);
    }
}
