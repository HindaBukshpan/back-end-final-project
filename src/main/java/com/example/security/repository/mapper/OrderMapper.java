package com.example.security.repository.mapper;

import com.example.security.model.Order;
import com.example.security.model.OrderItem;
import com.example.security.model.Role;
import com.example.security.model.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setUsername(rs.getString("username"));
        order.setOrderDate(rs.getString("order_date")); // או אם אתה רוצה להמיר לתאריך (Date)
        Status.valueOf( rs.getString("status"));
        return order;
    }
}

