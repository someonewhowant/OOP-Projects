package com.grocerystore.service;

import com.grocerystore.exception.ItemNotFoundException;
import com.grocerystore.model.Item;
import com.grocerystore.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatalogService {
    private final ItemRepository itemRepository;

    public CatalogService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void updateItem(Item item) {
        if (getItem(item.getBarcode()).isEmpty()) {
            throw new ItemNotFoundException("Item with barcode " + item.getBarcode() + " not found in catalog");
        }
        itemRepository.save(item);
    }

    public void deleteItem(String barcode) {
        itemRepository.delete(barcode);
    }

    public Optional<Item> getItem(String barcode) {
        return itemRepository.findByBarcode(barcode);
    }
}
