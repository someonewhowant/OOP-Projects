package com.grocerystore.repository.impl;

import com.grocerystore.repository.InventoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public InventoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveStock(String barcode, int quantity) {
        String sql = "INSERT OR REPLACE INTO inventory (barcode, quantity) VALUES (?, ?)";
        jdbcTemplate.update(sql, barcode, quantity);
    }

    @Override
    public int getStock(String barcode) {
        String sql = "SELECT quantity FROM inventory WHERE barcode = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("quantity"), barcode)
                .stream().findFirst().orElse(0);
    }
}
