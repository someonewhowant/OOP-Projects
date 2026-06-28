package com.grocerystore.discount;

public class PercentageDiscountStrategy implements DiscountCalculationStrategy {
    private final double percentage;

    public PercentageDiscountStrategy(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double price, int quantity) {
        return price * quantity * (percentage / 100.0);
    }
}
