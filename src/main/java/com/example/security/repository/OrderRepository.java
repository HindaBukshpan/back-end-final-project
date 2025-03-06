package com.example.security.repository;

import com.example.security.model.Order;
import com.example.security.model.OrderItem;
import com.example.security.repository.mapper.OrderMapper;
import com.example.security.repository.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ORDERS_TABLE = "orders";
    private static final String ORDER_ITEMS_TABLE = "order_items";

    public List<Order> getOrdersFromUser(String username) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE username = ?", ORDERS_TABLE);
            return jdbcTemplate.query(sql, new OrderMapper(), username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
        public Order createOrder(Order order) {
            String sql = "INSERT INTO " + ORDERS_TABLE + " (username) VALUES (?)";

            jdbcTemplate.update(sql, order.getUsername());

            String getLastInsertedIdSql = "SELECT LAST_INSERT_ID()";
            Long newOrderId = jdbcTemplate.queryForObject(getLastInsertedIdSql, Long.class);

            order.setId(newOrderId);

            return order;
        }

        public Order findOrderById(Long orderId) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ?", ORDERS_TABLE);
            Order order = jdbcTemplate.queryForObject(sql, new OrderMapper(), orderId);
            return order;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String updateOrder(Long orderId, Order updatedOrder) {
        String sql = String.format("UPDATE %s SET username = ? WHERE id = ?", ORDERS_TABLE);
        jdbcTemplate.update(sql, updatedOrder.getUsername(), orderId);
        return "Order updated successfully";
    }

    public String deleteOrder(Long orderId) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", ORDERS_TABLE);
        jdbcTemplate.update(sql, orderId);
        return "Order deleted successfully";
    }

    public String addItemToOrder(Long orderId, OrderItem orderItem) {
        String sql = String.format("INSERT INTO %s (order_id, product_name, quantity) VALUES (?, ?, ?)", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, orderId, orderItem.getItemId(), orderItem.getQuantity());
        return "Item added to order successfully";
    }

    public String deleteItemFromOrder(Long orderId, Long itemId) {
        String sql = String.format("DELETE FROM %s WHERE order_id = ? AND id = ?", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, orderId, itemId);
        return "Item deleted from order successfully";
    }

    public List<OrderItem> findItemsByOrderId(Long orderId) {
        String sql = String.format("SELECT * FROM %s WHERE order_id = ?", ORDER_ITEMS_TABLE);
        return jdbcTemplate.query(sql, new OrderItemMapper(), orderId);
    }

    public String deleteItemsByUsername(String username) {
        String sql = String.format("DELETE * FROM %s WHERE username = ?", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, username);
        return "the items where delete successfully";
    }

    public String deleteOrdersByUsername(String username) {
        String sql = String.format("DELETE * FROM %s WHERE username = ?", ORDERS_TABLE);
        jdbcTemplate.update(sql, username);
        return "the orders where delete successfully";
    }

    public Order checkOpenOrder(String username) {
        String sql = String.format("SELECT * FROM %s WHERE username = ? AND status = 'PENDING' LIMIT 1", ORDERS_TABLE);
        try {
            return jdbcTemplate.queryForObject(sql, new OrderMapper(), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}

