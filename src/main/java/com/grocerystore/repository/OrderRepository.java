package com.grocerystore.repository;

import com.grocerystore.order.Order;

public interface OrderRepository {
    void save(Order order);
}
