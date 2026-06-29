package com.grocerystore.service;

import com.grocerystore.exception.InsufficientStockException;
import com.grocerystore.exception.ItemNotFoundException;
import com.grocerystore.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final CatalogService catalogService;

    public InventoryService(InventoryRepository inventoryRepository, CatalogService catalogService) {
        this.inventoryRepository = inventoryRepository;
        this.catalogService = catalogService;
    }

    public void addStock(String barcode, int quantity) {
        if (catalogService.getItem(barcode).isEmpty()) {
            throw new ItemNotFoundException("Item with barcode " + barcode + " not found in catalog");
        }
        int currentStock = getStock(barcode);
        inventoryRepository.saveStock(barcode, currentStock + quantity);
    }

    public void reduceStock(String barcode, int quantity) {
        int currentStock = getStock(barcode);
        if (currentStock < quantity) {
            throw new InsufficientStockException("Not enough stock for barcode " + barcode);
        }
        inventoryRepository.saveStock(barcode, currentStock - quantity);
    }

    public int getStock(String barcode) {
        return inventoryRepository.getStock(barcode);
    }

    public java.util.Map<String, Integer> getAllStock() {
        return inventoryRepository.findAllStock();
    }
}
