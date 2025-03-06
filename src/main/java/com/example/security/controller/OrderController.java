package com.example.security.controller;
import com.example.security.model.Order;
import com.example.security.model.OrderItem;
import com.example.security.service.OrderService;
import com.example.security.service.OrderItemService;
import com.example.security.service.UserService;
import com.example.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
    @RequestMapping(value = "/orders")
    @CrossOrigin(origins = "http://localhost:3000")
    public class OrderController {

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private UserService userService;

        @Autowired
        private OrderService orderService;

        @Autowired
        private OrderItemService orderItemService;
 @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/check")
    public ResponseEntity<?> checkOpenOrder(@RequestHeader(value = "Authorization") String token, @RequestBody OrderItem orderItem) {
        try {
            System.out.println(orderItem);
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

            Order orderFromDb = orderService.checkOpenOrder(username);
            System.out.println(orderFromDb);
            if (orderFromDb == null) {
                System.out.println(2222);
                Order newOrder = new Order();
                newOrder.setUsername(username);
                newOrder.setOrderDate(LocalDate.now().toString());
                newOrder.setItems(orderItem);
                System.out.println(33333);
                Order createdOrder = orderService.createOrder(newOrder);

                orderService.addItemToOrder(createdOrder.getId(), orderItem);

                return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
            }

            System.out.println(333);

            orderService.addItemToOrder(orderFromDb.getId(), orderItem);

            return ResponseEntity.ok("Order updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

        @GetMapping
        public ResponseEntity getOrdersByUser(@RequestHeader(value = "Authorization") String token){
            try {
                String jwtToken = token.substring(7);  // חיתוך הטוקן
                String username = jwtUtil.extractUsername(jwtToken);
                orderService.getOrdersFromUser(username);
                return new ResponseEntity<>(orderService.getOrdersFromUser(username), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        @GetMapping("/{orderId}")
        public ResponseEntity<Order> getOrderById(@PathVariable("orderId") Long orderId) {
            try {
                Order order = orderService.getOrderById(orderId);
                if (order != null) {
                    return ResponseEntity.ok(order);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // עדכון הזמנה
        @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        @PutMapping("/{orderId}")
        public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody Order updatedOrder) {
            try {
                Order order = orderService.updateOrder(orderId, updatedOrder);
                if (order != null) {
                    return ResponseEntity.ok(order);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // מחיקת הזמנה
        @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        @DeleteMapping("/{orderId}")
        public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {
            try {
                String result = orderService.deleteOrder(orderId);
                if (result.contains("successfully")) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        @PostMapping("/{orderId}/items")
        public ResponseEntity<String> addItemToOrder(@PathVariable("orderId") Long orderId, @RequestBody OrderItem orderItem) {
            try {
                String result = orderItemService.addItemToOrder(orderId, orderItem);
                if (result.contains("successfully")) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        @DeleteMapping("/{orderId}/items/{itemId}")
        public ResponseEntity<String> deleteItemFromOrder(@PathVariable("orderId") Long orderId, @PathVariable("itemId") Long itemId) {
            try {
                String result = orderItemService.deleteItemFromOrder(orderId, itemId);
                if (result.contains("successfully")) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

}
