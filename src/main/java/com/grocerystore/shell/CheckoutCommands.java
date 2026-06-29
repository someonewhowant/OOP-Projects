package com.grocerystore.shell;

import com.grocerystore.order.Checkout;
import com.grocerystore.order.Order;
import com.grocerystore.order.Receipt;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;

@ShellComponent
public class CheckoutCommands {
    private final Checkout checkout;
    private Order currentOrder;

    public CheckoutCommands(Checkout checkout) {
        this.checkout = checkout;
    }

    @ShellMethod(key = {"start", "new"}, value = "Start a new customer order")
    public String startOrder() {
        this.currentOrder = checkout.createOrder();
        return "🛒 New order started! Ready to scan items.";
    }

    @ShellMethod(key = {"scan", "add"}, value = "Scan an item into the current order")
    public String scanItem(
            @ShellOption(valueProvider = BarcodeValueProvider.class, help = "Item barcode") String barcode,
            @ShellOption(defaultValue = "1", help = "Quantity") int quantity) {
        
        if (currentOrder == null) {
            return "❌ Error: No active order. Type 'start' to create a new order.";
        }
        
        try {
            checkout.scanItem(currentOrder, barcode, quantity);
            return String.format("✅ Added %d x [%s] to order. Current subtotal: $%s", 
                    quantity, barcode, currentOrder.calculateSubtotal());
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    @ShellMethod(key = {"pay", "checkout"}, value = "Process payment and complete the order")
    public String processPayment(
            @ShellOption(help = "Amount paid by customer") double amount) {
        
        if (currentOrder == null) {
            return "❌ Error: No active order. Type 'start' to create a new order.";
        }
        
        try {
            Receipt receipt = checkout.processPayment(currentOrder, BigDecimal.valueOf(amount));
            this.currentOrder = null; // Reset for next customer
            return "\n" + receipt.generateReceipt();
        } catch (Exception e) {
            return "❌ Checkout failed: " + e.getMessage();
        }
    }
}
