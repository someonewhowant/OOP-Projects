package com.grocerystore.shell;

import com.grocerystore.model.Item;
import com.grocerystore.service.CatalogService;
import com.grocerystore.service.InventoryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;

@ShellComponent
public class AdminCommands {
    private final CatalogService catalogService;
    private final InventoryService inventoryService;

    public AdminCommands(CatalogService catalogService, InventoryService inventoryService) {
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
    }

    @ShellMethod(key = {"item add", "add-item"}, value = "Add a new item to the catalog")
    public String addItem(String barcode, String name, String category, double price) {
        Item item = new Item(name, barcode, category, BigDecimal.valueOf(price));
        catalogService.addItem(item);
        return String.format("✅ Item added: %s (%s) - $%s", name, barcode, price);
    }

    @ShellMethod(key = {"stock add", "add-stock"}, value = "Add quantity to stock")
    public String addStock(String barcode, int quantity) {
        try {
            inventoryService.addStock(barcode, quantity);
            return String.format("📦 Added %d units to barcode %s. New total: %d", 
                    quantity, barcode, inventoryService.getStock(barcode));
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    @ShellMethod(key = {"stock view", "view-stock"}, value = "View current stock for an item")
    public String viewStock(String barcode) {
        int stock = inventoryService.getStock(barcode);
        return String.format("📊 Current stock for barcode %s: %d units", barcode, stock);
    }
}
