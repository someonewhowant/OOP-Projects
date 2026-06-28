package com.grocerystore;

import com.grocerystore.discount.DiscountCampaign;
import com.grocerystore.model.Catalog;
import com.grocerystore.model.Inventory;
import com.grocerystore.model.Item;
import com.grocerystore.order.Checkout;
import com.grocerystore.order.Order;
import com.grocerystore.order.Receipt;

public class GroceryStoreSystem {
    private final Catalog catalog;
    private final Inventory inventory;
    private final Checkout checkout;

    public GroceryStoreSystem() {
        this.catalog = new Catalog();
        this.inventory = new Inventory(this.catalog);
        this.checkout = new Checkout(this.catalog, this.inventory);
    }

    public void addItemToCatalog(Item item) {
        catalog.addItem(item);
    }

    public void addStock(String barcode, int quantity) {
        inventory.addStock(barcode, quantity);
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

    public Receipt checkoutOrder(Order order, double amountPaid) {
        return checkout.processPayment(order, amountPaid);
    }
}
