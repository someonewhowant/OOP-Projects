package com.grocerystore.order;

public class Receipt {
    private final Order order;
    private final double amountPaid;

    public Receipt(Order order, double amountPaid) {
        this.order = order;
        this.amountPaid = amountPaid;
    }

    public void printReceipt() {
        System.out.println("=== GROCERY STORE RECEIPT ===");
        for (OrderItem item : order.getItems()) {
            System.out.printf("%s x %d - $%.2f%n", item.getItem().getName(), item.getQuantity(), item.getSubtotal());
        }
        System.out.println("-----------------------------");
        System.out.printf("Subtotal: $%.2f%n", order.calculateSubtotal());
        System.out.printf("Discount: -$%.2f%n", order.calculateTotalDiscount());
        System.out.printf("Total:    $%.2f%n", order.calculateTotal());
        System.out.println("-----------------------------");
        System.out.printf("Paid:     $%.2f%n", amountPaid);
        System.out.printf("Change:   $%.2f%n", amountPaid - order.calculateTotal());
        System.out.println("=============================");
    }
}
