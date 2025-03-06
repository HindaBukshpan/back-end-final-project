package com.example.security.service;

import com.example.security.model.Order;
import com.example.security.model.OrderItem;
import com.example.security.repository.OrderRepository;
import com.example.security.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

        public Order createOrder(Order order) {
            if (order.getUsername() == null) {
                throw new IllegalArgumentException("Username is required");
            }
            return orderRepository.createOrder(order);
        }
        public Order getOrderById(Long orderId) {
        return orderRepository.findOrderById(orderId);
    }

    public Order updateOrder(Long orderId, Order updatedOrder) {
        try {
            Order existingOrder = orderRepository.findOrderById(orderId);
            if (existingOrder != null) {
                updatedOrder.setId(orderId);
                orderRepository.updateOrder(orderId, updatedOrder);
                return orderRepository.findOrderById(orderId);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating order: " + e.getMessage());
        }
    }


    public String deleteOrder(Long orderId) {
        try {
            orderRepository.deleteOrder(orderId);
            return "Order deleted successfully";
        } catch (Exception e) {
            return "Error deleting order: " + e.getMessage();
        }
    }

    public String addItemToOrder(Long orderId, OrderItem orderItem) {
        try {
            orderItem.setOrderId(orderId); 
            orderItemRepository.save(orderItem);
            return "Item added to order successfully";
        } catch (Exception e) {
            return "Error adding item to order: " + e.getMessage();
        }
    }


    public String deleteItemFromOrder(Long orderId, Long itemId) {
        try {
            Order order = orderRepository.findOrderById(orderId);
            if (order == null) {
                return "Order not found";
            }

            // חפש את פריט ההזמנה לפי מזהה הפריט
            OrderItem orderItem = orderItemRepository.findById(itemId).orElse(null);
            if (orderItem == null || !orderItem.getOrderId().equals(orderId)) {
                return "Item not found in order";
            }

            // אם הפריט שייך להזמנה, מחק אותו
            orderItemRepository.delete(orderItem);
            return "Item deleted from order successfully";
        } catch (Exception e) {
            return "Error deleting item from order: " + e.getMessage();
        }


    }

    public Order checkOpenOrder(String username) {
      return orderRepository.checkOpenOrder(username);
    }

    public String deleteUserDetailsWhenDeleteTheUser(String username){
        if (username == null){ return "please enter the system";}
        orderRepository.deleteItemsByUsername(username);
        orderRepository.deleteOrdersByUsername(username);
        return "the users orders and items where successfully deleted";
    }

    public List<Order> getOrdersFromUser(String username){
        return orderRepository.getOrdersFromUser(username);
    }


}
