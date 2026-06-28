package com.grocerystore.repository.sqlite;

import com.grocerystore.DatabaseManager;
import com.grocerystore.exception.DataAccessException;
import com.grocerystore.repository.InventoryRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteInventoryRepository implements InventoryRepository {
    @Override
    public void saveStock(String barcode, int quantity) {
        String sql = "INSERT OR REPLACE INTO inventory (barcode, quantity) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barcode);
            pstmt.setInt(2, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error saving stock to repository", e);
        }
    }

    @Override
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
            throw new DataAccessException("Error retrieving stock from repository", e);
        }
        return 0;
    }
}
