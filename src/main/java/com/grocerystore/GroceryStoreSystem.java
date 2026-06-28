package com.grocerystore;

import com.grocerystore.discount.DiscountCampaign;
import com.grocerystore.model.Item;
import com.grocerystore.order.Checkout;
import com.grocerystore.order.Order;
import com.grocerystore.order.Receipt;
import com.grocerystore.repository.InventoryRepository;
import com.grocerystore.repository.ItemRepository;
import com.grocerystore.repository.sqlite.SqliteInventoryRepository;
import com.grocerystore.repository.sqlite.SqliteItemRepository;
import com.grocerystore.service.CatalogService;
import com.grocerystore.service.InventoryService;

import java.math.BigDecimal;

public class GroceryStoreSystem {
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final Checkout checkout;

    public GroceryStoreSystem() {
        // Dependency Injection
        ItemRepository itemRepository = new SqliteItemRepository();
        InventoryRepository inventoryRepository = new SqliteInventoryRepository();
        
        this.catalogService = new CatalogService(itemRepository);
        this.inventoryService = new InventoryService(inventoryRepository, this.catalogService);
        this.checkout = new Checkout(this.catalogService, this.inventoryService);
    }

    public void addItemToCatalog(Item item) {
        catalogService.addItem(item);
    }

    public void addStock(String barcode, int quantity) {
        inventoryService.addStock(barcode, quantity);
    }

    public void addCampaign(DiscountCampaign campaign) {
        checkout.addCampaign(campaign);
    }

    public Order startOrder() {
        return checkout.createOrder();
    }

    public void scanItem(Order order, String barcode, int quantity) {
        checkout.scanItem(order, barcode, quantity);
    }

    public Receipt checkoutOrder(Order order, BigDecimal amountPaid) {
        return checkout.processPayment(order, amountPaid);
    }
}
