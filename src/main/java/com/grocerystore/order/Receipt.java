package com.grocerystore.order;

import java.math.BigDecimal;

public class Receipt {
    private final Order order;
    private final BigDecimal amountPaid;

    public Receipt(Order order, BigDecimal amountPaid) {
        this.order = order;
        this.amountPaid = amountPaid;
    }

    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GROCERY STORE RECEIPT ===\n");
        for (OrderItem item : order.getItems()) {
            sb.append(String.format("%s x %d - $%.2f%n", item.getItem().getName(), item.getQuantity(), item.getSubtotal()));
        }
        sb.append("-----------------------------\n");
        sb.append(String.format("Subtotal: $%.2f%n", order.calculateSubtotal()));
        sb.append(String.format("Discount: -$%.2f%n", order.calculateTotalDiscount()));
        sb.append(String.format("Total:    $%.2f%n", order.calculateTotal()));
        sb.append("-----------------------------\n");
        sb.append(String.format("Paid:     $%.2f%n", amountPaid));
        sb.append(String.format("Change:   $%.2f%n", amountPaid.subtract(order.calculateTotal())));
        sb.append("=============================\n");
        return sb.toString();
    }
}
