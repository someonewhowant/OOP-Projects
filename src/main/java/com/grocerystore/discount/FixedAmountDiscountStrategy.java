package com.grocerystore.discount;

public class FixedAmountDiscountStrategy implements DiscountCalculationStrategy {
    private final double fixedAmount;

    public FixedAmountDiscountStrategy(double fixedAmount) {
        if (fixedAmount < 0) {
            throw new IllegalArgumentException("Fixed amount cannot be negative");
        }
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double calculateDiscount(double price, int quantity) {
        return fixedAmount * quantity;
    }
}
