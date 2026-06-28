package com.grocerystore;

import com.grocerystore.discount.DiscountCampaign;
import com.grocerystore.model.Item;
import com.grocerystore.order.Checkout;
import com.grocerystore.order.Order;
import com.grocerystore.order.Receipt;
import com.grocerystore.repository.impl.InventoryRepositoryImpl;
import com.grocerystore.repository.impl.ItemRepositoryImpl;
import com.grocerystore.repository.impl.OrderRepositoryImpl;
import com.grocerystore.service.CatalogService;
import com.grocerystore.service.InventoryService;

import java.math.BigDecimal;

public class GroceryStoreSystem {
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final Checkout checkout;

    public GroceryStoreSystem() {
        // Dependency Injection
        ItemRepository itemRepository = new ItemRepositoryImpl();
        InventoryRepository inventoryRepository = new InventoryRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        
        this.catalogService = new CatalogService(itemRepository);
        this.inventoryService = new InventoryService(inventoryRepository, this.catalogService);
        this.checkout = new Checkout(this.catalogService, this.inventoryService, orderRepository);
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
