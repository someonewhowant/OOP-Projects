package com.grocerystore.model;

import java.math.BigDecimal;

public class Item {
    private String name;
    private String barcode;
    private String category;
    private BigDecimal price;

    public Item(String name, String barcode, String category, BigDecimal price) {
        this.name = name;
        this.barcode = barcode;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
