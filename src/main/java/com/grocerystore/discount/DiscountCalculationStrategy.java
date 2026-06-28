package com.grocerystore.discount;

import java.math.BigDecimal;

public interface DiscountCalculationStrategy {
    BigDecimal calculateDiscount(BigDecimal price, int quantity);
}
