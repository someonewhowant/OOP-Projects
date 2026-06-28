package com.grocerystore.model;

import java.math.BigDecimal;

public class Item {
    private final String name;
    private final String barcode;
    private final String category;
    private final BigDecimal price;

    public Item(String name, String barcode, String category, BigDecimal price) {
        this.name = name;
        this.barcode = barcode;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
