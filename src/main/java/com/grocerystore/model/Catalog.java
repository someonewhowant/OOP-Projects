package com.grocerystore.model;

import com.grocerystore.exception.ItemNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Catalog {
    private final Map<String, Item> items;

    public Catalog() {
        this.items = new HashMap<>();
    }

    public void addItem(Item item) {
        items.put(item.getBarcode(), item);
    }

    public void updateItem(Item item) {
        if (items.containsKey(item.getBarcode())) {
            items.put(item.getBarcode(), item);
        } else {
            throw new ItemNotFoundException("Item with barcode " + item.getBarcode() + " not found in catalog");
        }
    }

    public void deleteItem(String barcode) {
        items.remove(barcode);
    }

    public Optional<Item> getItem(String barcode) {
        return Optional.ofNullable(items.get(barcode));
    }
}
