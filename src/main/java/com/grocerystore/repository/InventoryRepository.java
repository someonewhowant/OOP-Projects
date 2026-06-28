package com.grocerystore.repository;

public interface InventoryRepository {
    void saveStock(String barcode, int quantity);
    int getStock(String barcode);
}
