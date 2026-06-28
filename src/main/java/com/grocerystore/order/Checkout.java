package com.grocerystore.order;

import com.grocerystore.discount.DiscountCampaign;
import com.grocerystore.exception.InsufficientStockException;
import com.grocerystore.exception.ItemNotFoundException;
import com.grocerystore.exception.PaymentFailedException;
import com.grocerystore.model.Item;
import com.grocerystore.repository.OrderRepository;
import com.grocerystore.service.CatalogService;
import com.grocerystore.service.InventoryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Checkout {
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    private final List<DiscountCampaign> activeCampaigns;

    public Checkout(CatalogService catalogService, InventoryService inventoryService, OrderRepository orderRepository) {
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
        this.orderRepository = orderRepository;
        this.activeCampaigns = new ArrayList<>();
    }

    public void addCampaign(DiscountCampaign campaign) {
        this.activeCampaigns.add(campaign);
    }

    public Order createOrder() {
        return new Order();
    }

    public void scanItem(Order order, String barcode, int quantity) {
        Optional<Item> itemOpt = catalogService.getItem(barcode);
        if (itemOpt.isEmpty()) {
            throw new ItemNotFoundException("Item with barcode " + barcode + " not found in catalog");
        }
        
        // Временно не списываем товар здесь. Списание произойдет при успешной оплате.
        order.addItem(new OrderItem(itemOpt.get(), quantity));
    }

    public Receipt processPayment(Order order, BigDecimal amountPaid) {
        for (DiscountCampaign campaign : activeCampaigns) {
            order.applyCampaign(campaign);
        }

        BigDecimal total = order.calculateTotal();
        if (amountPaid.compareTo(total) < 0) {
            throw new PaymentFailedException("Insufficient amount paid");
        }

        // 1. Проверяем наличие всех товаров перед списанием
        for (OrderItem orderItem : order.getItems()) {
            int currentStock = inventoryService.getStock(orderItem.getItem().getBarcode());
            if (currentStock < orderItem.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for barcode " + orderItem.getItem().getBarcode());
            }
        }

        // 2. Списываем товары со склада (если проверка пройдена успешно)
        for (OrderItem orderItem : order.getItems()) {
            inventoryService.reduceStock(orderItem.getItem().getBarcode(), orderItem.getQuantity());
        }

        // 3. Сохраняем заказ в базу данных
        orderRepository.save(order);

        return new Receipt(order, amountPaid);
    }
}
