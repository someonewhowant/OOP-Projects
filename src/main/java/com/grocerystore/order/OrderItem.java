package com.grocerystore.order;

import com.grocerystore.model.Item;
import java.math.BigDecimal;

public class OrderItem {
    private final Item item;
    private final int quantity;

    public OrderItem(Item item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSubtotal() {
        return item.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
