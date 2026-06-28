package com.grocerystore;

import com.grocerystore.discount.CategoryBasedCriteria;
import com.grocerystore.discount.DiscountCampaign;
import com.grocerystore.discount.FixedAmountDiscountStrategy;
import com.grocerystore.discount.ItemBasedCriteria;
import com.grocerystore.discount.PercentageDiscountStrategy;
import com.grocerystore.model.Item;
import com.grocerystore.order.Order;
import com.grocerystore.order.Receipt;

public class App {
    public static void main(String[] args) {
        System.out.println("Initializing Grocery Store System...");

        GroceryStoreSystem store = new GroceryStoreSystem();

        // 1. Управление каталогом
        Item apple = new Item("Apple", "12345", "Fruits", 1.50);
        Item milk = new Item("Milk", "67890", "Dairy", 2.00);
        Item bread = new Item("Bread", "11111", "Bakery", 1.20);
        
        store.addItemToCatalog(apple);
        store.addItemToCatalog(milk);
        store.addItemToCatalog(bread);

        // 2. Управление запасами
        store.addStock("12345", 100);
        store.addStock("67890", 50);
        store.addStock("11111", 30);

        // 3. Активные скидочные кампании
        // 10% off on all Dairy
        DiscountCampaign dairyDiscount = new DiscountCampaign(
                new CategoryBasedCriteria("Dairy"),
                new PercentageDiscountStrategy(10)
        );
        // $0.50 off on Bread
        DiscountCampaign breadDiscount = new DiscountCampaign(
                new ItemBasedCriteria("11111"),
                new FixedAmountDiscountStrategy(0.50)
        );
        
        store.addCampaign(dairyDiscount);
        store.addCampaign(breadDiscount);

        // 4. Оформление заказа
        try {
            Order myOrder = store.startOrder();
            store.scanItem(myOrder, "12345", 5); // 5 apples = $7.50
            store.scanItem(myOrder, "67890", 2); // 2 milk = $4.00, discount 10% = $0.40 -> $3.60
            store.scanItem(myOrder, "11111", 1); // 1 bread = $1.20, discount $0.50 -> $0.70
            // Subtotal = 12.70
            // Total discount = 0.90
            // Total = 11.80

            // 5. Оплата
            Receipt receipt = store.checkoutOrder(myOrder, 15.00);
            receipt.printReceipt();
        } catch (Exception e) {
            System.err.println("Error during checkout: " + e.getMessage());
        }
    }
}
