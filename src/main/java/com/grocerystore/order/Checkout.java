package com.grocerystore.order;

import com.grocerystore.discount.DiscountCampaign;
import com.grocerystore.model.Catalog;
import com.grocerystore.model.Inventory;
import com.grocerystore.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Checkout {
    private final Catalog catalog;
    private final Inventory inventory;
    private final List<DiscountCampaign> activeCampaigns;

    public Checkout(Catalog catalog, Inventory inventory) {
        this.catalog = catalog;
        this.inventory = inventory;
        this.activeCampaigns = new ArrayList<>();
    }

    public void addCampaign(DiscountCampaign campaign) {
        this.activeCampaigns.add(campaign);
    }

    public Order createOrder() {
        return new Order();
    }

    public void scanItem(Order order, String barcode, int quantity) {
        Optional<Item> itemOpt = catalog.getItem(barcode);
        if (itemOpt.isEmpty()) {
            throw new IllegalArgumentException("Item with barcode " + barcode + " not found in catalog");
        }
        
        inventory.reduceStock(barcode, quantity);
        order.addItem(new OrderItem(itemOpt.get(), quantity));
    }

    public Receipt processPayment(Order order, BigDecimal amountPaid) {
        for (DiscountCampaign campaign : activeCampaigns) {
            order.applyCampaign(campaign);
        }

        BigDecimal total = order.calculateTotal();
        if (amountPaid.compareTo(total) < 0) {
            throw new IllegalArgumentException("Insufficient amount paid");
        }

        return new Receipt(order, amountPaid);
    }
}
