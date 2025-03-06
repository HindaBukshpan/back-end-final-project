package com.example.security.service;

import com.example.security.model.OrderItem;
import com.example.security.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // הוספת פריט להזמנה
    public String addItemToOrder(Long orderId, OrderItem orderItem) {
        try {
            // כאן תוכל להוסיף גם לוודא שהפריט שנוסף שייך להזמנה
            orderItem.setOrderId(orderId);  // הגדרת מזהה הזמנה לפריט
            orderItemRepository.save(orderItem);
            return "Item added to order successfully";
        } catch (Exception e) {
            return "Error adding item to order: " + e.getMessage();
        }
    }

    // מחיקת פריט מההזמנה
    public String deleteItemFromOrder(Long orderId, Long itemId) {
        try {
            OrderItem orderItem = orderItemRepository.findById(itemId).orElse(null);
            if (orderItem == null || !orderItem.getOrderId().equals(orderId)) {
                return "Item not found in order";
            }

            orderItemRepository.delete(orderItem);
            return "Item deleted from order successfully";
        } catch (Exception e) {
            return "Error deleting item from order: " + e.getMessage();
        }
    }
}
