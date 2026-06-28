package com.grocerystore.discount;

import com.grocerystore.model.Item;

public class ItemBasedCriteria implements DiscountCriteria {
    private final String barcode;

    public ItemBasedCriteria(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public boolean isApplicable(Item item) {
        return item != null && barcode.equals(item.getBarcode());
    }
}
