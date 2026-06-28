package com.grocerystore.repository;

import com.grocerystore.model.Item;
import java.util.Optional;

public interface ItemRepository {
    void save(Item item);
    void delete(String barcode);
    Optional<Item> findByBarcode(String barcode);
}
