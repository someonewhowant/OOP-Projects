package com.grocerystore.repository.impl;

import com.grocerystore.DatabaseManager;
import com.grocerystore.exception.DataAccessException;
import com.grocerystore.order.Order;
import com.grocerystore.order.OrderItem;
import com.grocerystore.repository.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public void save(Order order) {
        String insertOrderSql = "INSERT INTO orders (subtotal, discount, total) VALUES (?, ?, ?)";
        String insertItemSql = "INSERT INTO order_items (order_id, barcode, name, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false); // Запуск транзакции для сохранения заказа и позиций
            
            try {
                long orderId;
                // Вставка заказа
                try (PreparedStatement pstmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, order.calculateSubtotal().toString());
                    pstmt.setString(2, order.calculateTotalDiscount().toString());
                    pstmt.setString(3, order.calculateTotal().toString());
                    pstmt.executeUpdate();
                    
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getLong(1);
                            order.setId(orderId);
                        } else {
                            throw new SQLException("Creating order failed, no ID obtained.");
                        }
                    }
                }
                
                // Вставка списка товаров (batch insert)
                try (PreparedStatement pstmt = conn.prepareStatement(insertItemSql)) {
                    for (OrderItem item : order.getItems()) {
                        pstmt.setLong(1, orderId);
                        pstmt.setString(2, item.getItem().getBarcode());
                        pstmt.setString(3, item.getItem().getName());
                        pstmt.setInt(4, item.getQuantity());
                        pstmt.setString(5, item.getItem().getPrice().toString());
                        pstmt.setString(6, item.getSubtotal().toString());
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
                
                conn.commit(); // Подтверждение транзакции
            } catch (SQLException e) {
                conn.rollback(); // Откат при любой ошибке
                throw new DataAccessException("Error saving order, transaction rolled back", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error in OrderRepository", e);
        }
    }
}
