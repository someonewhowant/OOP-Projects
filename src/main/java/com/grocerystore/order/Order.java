package com.grocerystore.order;

import com.grocerystore.discount.DiscountCampaign;

import java.math.BigDecimal;
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

    public BigDecimal calculateSubtotal() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotal() {
        BigDecimal subtotal = calculateSubtotal();
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (OrderItem orderItem : items) {
            for (DiscountCampaign campaign : appliedCampaigns) {
                totalDiscount = totalDiscount.add(
                    campaign.applyDiscount(orderItem.getItem(), orderItem.getItem().getPrice(), orderItem.getQuantity())
                );
            }
        }

        BigDecimal total = subtotal.subtract(totalDiscount);
        return total.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : total;
    }

    public BigDecimal calculateTotalDiscount() {
        return calculateSubtotal().subtract(calculateTotal());
    }
}
