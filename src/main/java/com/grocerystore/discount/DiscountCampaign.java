package com.grocerystore.discount;

import com.grocerystore.model.Item;
import java.math.BigDecimal;

public class DiscountCampaign {
    private final DiscountCriteria criteria;
    private final DiscountCalculationStrategy strategy;

    public DiscountCampaign(DiscountCriteria criteria, DiscountCalculationStrategy strategy) {
        this.criteria = criteria;
        this.strategy = strategy;
    }

    public boolean isApplicable(Item item) {
        return criteria.isApplicable(item);
    }

    public BigDecimal applyDiscount(Item item, BigDecimal price, int quantity) {
        if (isApplicable(item)) {
            return strategy.calculateDiscount(price, quantity);
        }
        return BigDecimal.ZERO;
    }
}
