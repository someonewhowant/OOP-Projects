package com.grocerystore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
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
                    
            // Create orders table
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "subtotal TEXT NOT NULL," +
                    "discount TEXT NOT NULL," +
                    "total TEXT NOT NULL," +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)");

            // Create order_items table
            stmt.execute("CREATE TABLE IF NOT EXISTS order_items (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "order_id INTEGER NOT NULL," +
                    "barcode TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "price TEXT NOT NULL," +
                    "subtotal TEXT NOT NULL," +
                    "FOREIGN KEY (order_id) REFERENCES orders(id))");
                    
        } catch (SQLException e) {
            logger.error("Failed to initialize database: {}", e.getMessage(), e);
        }
    }
}
