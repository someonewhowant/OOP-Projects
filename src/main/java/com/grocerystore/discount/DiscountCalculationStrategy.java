package com.grocerystore.discount;

public interface DiscountCalculationStrategy {
    double calculateDiscount(double price, int quantity);
}
