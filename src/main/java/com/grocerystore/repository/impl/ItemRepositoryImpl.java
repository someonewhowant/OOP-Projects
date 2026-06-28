package com.grocerystore.repository.impl;

import com.grocerystore.DatabaseManager;
import com.grocerystore.exception.DataAccessException;
import com.grocerystore.model.Item;
import com.grocerystore.repository.ItemRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public void save(Item item) {
        String sql = "INSERT OR REPLACE INTO items (barcode, name, category, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getBarcode());
            pstmt.setString(2, item.getName());
            pstmt.setString(3, item.getCategory());
            pstmt.setString(4, item.getPrice().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error saving item to repository", e);
        }
    }

    @Override
    public void delete(String barcode) {
        String sql = "DELETE FROM items WHERE barcode = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barcode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting item from repository", e);
        }
    }

    @Override
    public Optional<Item> findByBarcode(String barcode) {
        String sql = "SELECT name, barcode, category, price FROM items WHERE barcode = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barcode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item(
                            rs.getString("name"),
                            rs.getString("barcode"),
                            rs.getString("category"),
                            new BigDecimal(rs.getString("price"))
                    );
                    return Optional.of(item);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving item from repository", e);
        }
        return Optional.empty();
    }
}
