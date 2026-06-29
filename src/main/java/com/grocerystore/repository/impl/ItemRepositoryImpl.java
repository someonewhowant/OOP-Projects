package com.grocerystore.repository.impl;

import com.grocerystore.model.Item;
import com.grocerystore.repository.ItemRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public ItemRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Item item) {
        String sql = "INSERT OR REPLACE INTO items (barcode, name, category, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, item.getBarcode(), item.getName(), item.getCategory(), item.getPrice().toString());
    }

    @Override
    public void delete(String barcode) {
        String sql = "DELETE FROM items WHERE barcode = ?";
        jdbcTemplate.update(sql, barcode);
    }

    @Override
    public Optional<Item> findByBarcode(String barcode) {
        String sql = "SELECT name, barcode, category, price FROM items WHERE barcode = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Item(
                rs.getString("name"),
                rs.getString("barcode"),
                rs.getString("category"),
                new BigDecimal(rs.getString("price"))
        ), barcode).stream().findFirst();
    }

    @Override
    public java.util.List<Item> findAll() {
        String sql = "SELECT name, barcode, category, price FROM items";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Item(
                rs.getString("name"),
                rs.getString("barcode"),
                rs.getString("category"),
                new BigDecimal(rs.getString("price"))
        ));
    }
}
