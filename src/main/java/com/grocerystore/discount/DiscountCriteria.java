package com.grocerystore.discount;

import com.grocerystore.model.Item;

public interface DiscountCriteria {
    boolean isApplicable(Item item);
}
