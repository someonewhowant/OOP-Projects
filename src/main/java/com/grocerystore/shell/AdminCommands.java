package com.grocerystore.shell;

import com.grocerystore.model.Item;
import com.grocerystore.service.CatalogService;
import com.grocerystore.service.InventoryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;

import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModelBuilder;
import org.springframework.shell.table.BorderStyle;

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

    @ShellMethod(key = {"catalog", "view-catalog"}, value = "Show all items in the catalog as a formatted table")
    public String viewCatalog() {
        java.util.List<Item> items = catalogService.getAllItems();
        if (items.isEmpty()) {
            return "Catalog is currently empty.";
        }
        
        TableModelBuilder<Object> modelBuilder = new TableModelBuilder<>();
        modelBuilder.addRow().addValue("Barcode").addValue("Name").addValue("Category").addValue("Price");
        
        for (Item item : items) {
            modelBuilder.addRow()
                    .addValue(item.getBarcode())
                    .addValue(item.getName())
                    .addValue(item.getCategory())
                    .addValue("$" + item.getPrice().toString());
        }
        
        TableBuilder tableBuilder = new TableBuilder(modelBuilder.build());
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        return "\n" + tableBuilder.build().render(80);
    }

    @ShellMethod(key = {"inventory", "stock list"}, value = "Show all inventory stock as a formatted table")
    public String viewInventory() {
        java.util.Map<String, Integer> stockMap = inventoryService.getAllStock();
        if (stockMap.isEmpty()) {
            return "Inventory is currently empty.";
        }
        
        TableModelBuilder<Object> modelBuilder = new TableModelBuilder<>();
        modelBuilder.addRow().addValue("Barcode").addValue("Item Name").addValue("Stock Qty");
        
        for (java.util.Map.Entry<String, Integer> entry : stockMap.entrySet()) {
            String barcode = entry.getKey();
            int qty = entry.getValue();
            String name = catalogService.getItem(barcode).map(Item::getName).orElse("Unknown");
            
            String qtyStr = String.valueOf(qty);
            if (qty < 10) {
                qtyStr = "\u001B[31m" + qtyStr + " (LOW)\u001B[0m";
            } else {
                qtyStr = "\u001B[32m" + qtyStr + "\u001B[0m";
            }
            
            modelBuilder.addRow().addValue(barcode).addValue(name).addValue(qtyStr);
        }
        
        TableBuilder tableBuilder = new TableBuilder(modelBuilder.build());
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        return "\n" + tableBuilder.build().render(80);
    }
}
