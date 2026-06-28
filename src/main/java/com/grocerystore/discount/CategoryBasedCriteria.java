package com.grocerystore.discount;

import com.grocerystore.model.Item;

public class CategoryBasedCriteria implements DiscountCriteria {
    private final String category;

    public CategoryBasedCriteria(String category) {
        this.category = category;
    }

    @Override
    public boolean isApplicable(Item item) {
        return item != null && category.equals(item.getCategory());
    }
}
