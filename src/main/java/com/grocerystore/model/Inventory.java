package com.grocerystore.model;

import com.grocerystore.exception.InsufficientStockException;
import com.grocerystore.exception.ItemNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Integer> stock;
    private final Catalog catalog;

    public Inventory(Catalog catalog) {
        this.stock = new HashMap<>();
        this.catalog = catalog;
    }

    public void addStock(String barcode, int quantity) {
        if (catalog.getItem(barcode).isEmpty()) {
            throw new ItemNotFoundException("Item with barcode " + barcode + " not found in catalog");
        }
        stock.put(barcode, stock.getOrDefault(barcode, 0) + quantity);
    }

    public void reduceStock(String barcode, int quantity) {
        int currentStock = stock.getOrDefault(barcode, 0);
        if (currentStock < quantity) {
            throw new InsufficientStockException("Not enough stock for barcode " + barcode);
        }
        stock.put(barcode, currentStock - quantity);
    }

    public int getStock(String barcode) {
        return stock.getOrDefault(barcode, 0);
    }
}
