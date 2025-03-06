package com.example.security.repository;

import com.example.security.model.Item;
import com.example.security.repository.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ITEMS_TABLE = "items";

    public String createItem(Item item){
        String sql = String.format("INSERT INTO %s (name, description, price, stock, image_url) " +
                "VALUES (?, ?, ?, ?, ?)", ITEMS_TABLE);
        jdbcTemplate.update(sql, item.getName(), item.getDescription(), item.getPrice(), item.getStock(), item.getImageUrl());
        return "Item created successfully";
    }

    public Item findItemById(int itemId){
        String sql = String.format("SELECT * FROM %s WHERE id = ?", ITEMS_TABLE);
        return jdbcTemplate.queryForObject(sql, new ItemMapper(), itemId);
    }

    public List<Item> findAllItems(){
        String sql = String.format("SELECT * FROM %s", ITEMS_TABLE);
        List<Item> items = jdbcTemplate.query(sql, new ItemMapper());
        return items;
    }

    public List<Item> findItemBySearch(String search){

        String sql = String.format(
                "SELECT * FROM %s WHERE LOWER(name) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?)", ITEMS_TABLE);
        List<Item> items = jdbcTemplate.query(sql, new Object[]{"%" + search + "%", "%" + search + "%"}, new ItemMapper());
        return items;

    }

    public Item updateItem(Item item){
        String sql = String.format("UPDATE %s SET name = ?, description = ?, price = ?, stock = ?, image_url = ? WHERE id = ?", ITEMS_TABLE);
        jdbcTemplate.update(sql, item.getName(), item.getDescription(), item.getPrice(), item.getStock(), item.getImageUrl(), item.getId());
        return findItemById(item.getId());
    }

    public String deleteItem(int itemId){
        String sql = String.format("DELETE FROM %s WHERE id = ?", ITEMS_TABLE);
        jdbcTemplate.update(sql, itemId);
        return "item deleted successfully";
    }

}
