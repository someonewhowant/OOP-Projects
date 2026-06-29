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

    @ShellMethod(key = "add-item", value = "Add a new item to the catalog")
    public String addItem(
            @ShellOption(help = "Item barcode") String barcode,
            @ShellOption(help = "Item name") String name,
            @ShellOption(help = "Item category") String category,
            @ShellOption(help = "Item price") double price) {
        
        Item item = new Item(name, barcode, category, BigDecimal.valueOf(price));
        catalogService.addItem(item);
        return String.format("Successfully added item: %s (%s)", name, barcode);
    }

    @ShellMethod(key = "add-stock", value = "Add quantity to stock")
    public String addStock(
            @ShellOption(help = "Item barcode") String barcode,
            @ShellOption(help = "Quantity to add") int quantity) {
        
        inventoryService.addStock(barcode, quantity);
        return String.format("Successfully added %d items to stock for barcode %s", quantity, barcode);
    }

    @ShellMethod(key = "view-stock", value = "View current stock for an item")
    public String viewStock(
            @ShellOption(help = "Item barcode") String barcode) {
        
        int stock = inventoryService.getStock(barcode);
        return String.format("Current stock for barcode %s is: %d", barcode, stock);
    }
}
