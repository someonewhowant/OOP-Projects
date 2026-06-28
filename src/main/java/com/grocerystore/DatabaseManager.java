package com.grocerystore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:grocerystore.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create items table
            stmt.execute("CREATE TABLE IF NOT EXISTS items (" +
                    "barcode TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "category TEXT NOT NULL," +
                    "price TEXT NOT NULL)");
                    
            // Create inventory table
            stmt.execute("CREATE TABLE IF NOT EXISTS inventory (" +
                    "barcode TEXT PRIMARY KEY," +
                    "quantity INTEGER NOT NULL DEFAULT 0," +
                    "FOREIGN KEY (barcode) REFERENCES items(barcode))");
                    
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }
}
