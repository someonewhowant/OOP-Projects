package com.grocerystore.discount;

import java.math.BigDecimal;

public class FixedAmountDiscountStrategy implements DiscountCalculationStrategy {
    private final BigDecimal fixedAmount;

    public FixedAmountDiscountStrategy(BigDecimal fixedAmount) {
        if (fixedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fixed amount cannot be negative");
        }
        this.fixedAmount = fixedAmount;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal price, int quantity) {
        return fixedAmount.multiply(BigDecimal.valueOf(quantity));
    }
}
