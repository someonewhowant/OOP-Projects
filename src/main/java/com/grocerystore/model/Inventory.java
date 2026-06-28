package com.grocerystore.model;

import com.grocerystore.DatabaseManager;
import com.grocerystore.exception.InsufficientStockException;
import com.grocerystore.exception.ItemNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {
    private final Catalog catalog;

    public Inventory(Catalog catalog) {
        this.catalog = catalog;
    }

    public void addStock(String barcode, int quantity) {
        if (catalog.getItem(barcode).isEmpty()) {
            throw new ItemNotFoundException("Item with barcode " + barcode + " not found in catalog");
        }
        int currentStock = getStock(barcode);
        updateStockInDb(barcode, currentStock + quantity);
    }

    public void reduceStock(String barcode, int quantity) {
        int currentStock = getStock(barcode);
        if (currentStock < quantity) {
            throw new InsufficientStockException("Not enough stock for barcode " + barcode);
        }
        updateStockInDb(barcode, currentStock - quantity);
    }

    public int getStock(String barcode) {
        String sql = "SELECT quantity FROM inventory WHERE barcode = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barcode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stock", e);
        }
        return 0; // default to 0 if not present
    }

    private void updateStockInDb(String barcode, int quantity) {
        String sql = "INSERT OR REPLACE INTO inventory (barcode, quantity) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barcode);
            pstmt.setInt(2, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating stock", e);
        }
    }
}
