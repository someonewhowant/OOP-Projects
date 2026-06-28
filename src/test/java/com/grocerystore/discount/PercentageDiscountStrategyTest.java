package com.grocerystore.discount;

import com.grocerystore.model.Item;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentageDiscountStrategyTest {
    @Test
    void testApplyDiscount() {
        PercentageDiscountStrategy strategy = new PercentageDiscountStrategy(BigDecimal.valueOf(10)); // 10%
        Item item = new Item("Apple", "123", "Fruits", BigDecimal.valueOf(100));
        
        BigDecimal discount = strategy.calculateDiscount(item, item.getPrice(), 2);
        
        // 10% of 100 = 10. For 2 items = 20.
        assertEquals(0, BigDecimal.valueOf(20).compareTo(discount));
    }
}
