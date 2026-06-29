package com.grocerystore.repository.impl;

import com.grocerystore.order.Order;
import com.grocerystore.order.OrderItem;
import com.grocerystore.repository.OrderRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void save(Order order) {
        String insertOrderSql = "INSERT INTO orders (subtotal, discount, total) VALUES (?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.calculateSubtotal().toString());
            ps.setString(2, order.calculateTotalDiscount().toString());
            ps.setString(3, order.calculateTotal().toString());
            return ps;
        }, keyHolder);
        
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);
        
        String insertItemSql = "INSERT INTO order_items (order_id, barcode, name, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.batchUpdate(insertItemSql, order.getItems(), order.getItems().size(),
                (ps, item) -> {
                    ps.setLong(1, orderId);
                    ps.setString(2, item.getItem().getBarcode());
                    ps.setString(3, item.getItem().getName());
                    ps.setInt(4, item.getQuantity());
                    ps.setString(5, item.getItem().getPrice().toString());
                    ps.setString(6, item.getSubtotal().toString());
                });
    }
}
