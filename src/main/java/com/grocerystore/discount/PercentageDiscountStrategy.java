package com.grocerystore.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscountStrategy implements DiscountCalculationStrategy {
    private final BigDecimal percentage;

    public PercentageDiscountStrategy(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal price, int quantity) {
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));
        BigDecimal fraction = percentage.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        return totalAmount.multiply(fraction).setScale(2, RoundingMode.HALF_UP);
    }
}
