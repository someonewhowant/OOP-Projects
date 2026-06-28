package com.grocerystore.order;

import com.grocerystore.discount.DiscountCampaign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final List<OrderItem> items;
    private final List<DiscountCampaign> appliedCampaigns;

    public Order() {
        this.items = new ArrayList<>();
        this.appliedCampaigns = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void applyCampaign(DiscountCampaign campaign) {
        this.appliedCampaigns.add(campaign);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public List<DiscountCampaign> getAppliedCampaigns() {
        return Collections.unmodifiableList(appliedCampaigns);
    }

    public double calculateSubtotal() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public double calculateTotal() {
        double subtotal = calculateSubtotal();
        double totalDiscount = 0.0;

        for (OrderItem orderItem : items) {
            for (DiscountCampaign campaign : appliedCampaigns) {
                totalDiscount += campaign.applyDiscount(orderItem.getItem(), orderItem.getItem().getPrice(), orderItem.getQuantity());
            }
        }

        return Math.max(0, subtotal - totalDiscount);
    }

    public double calculateTotalDiscount() {
        return calculateSubtotal() - calculateTotal();
    }
}
