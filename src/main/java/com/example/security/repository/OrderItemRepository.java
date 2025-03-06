package com.example.security.repository;

import com.example.security.model.OrderItem;
import com.example.security.repository.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ORDER_ITEMS_TABLE = "ordered_items";

    public String save(OrderItem orderItem) {
        String sql = String.format("INSERT INTO %s (order_id, item_id, quantity, total_price_per_item) VALUES (?, ?, ?, ?)", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getItemId(), orderItem.getQuantity(), orderItem.getTotalPrice());
        return "Item added to order successfully";
    }

    public void delete(OrderItem orderItem) {
        String sql = String.format("DELETE FROM %s WHERE order_id = ? AND item_id = ?", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getItemId());
    }

    public Optional<OrderItem> findById(Long itemId) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE item_id = ?", ORDER_ITEMS_TABLE);
            OrderItem orderItem = jdbcTemplate.queryForObject(sql, new OrderItemMapper(), itemId);
            return Optional.ofNullable(orderItem);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<OrderItem> findItemsByOrderId(Long orderId) {
        String sql = String.format("SELECT * FROM %s WHERE order_id = ?", ORDER_ITEMS_TABLE);
        return jdbcTemplate.query(sql, new OrderItemMapper(), orderId);
    }
}
