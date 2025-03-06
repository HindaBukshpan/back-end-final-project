package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

        private Long id;
        private String username;
        private OrderItem items;  // רשימה של פריטי הזמנה
        @JsonProperty(value = "order_date")
        private String orderDate; // תאריך הזמנה
        private Status status;

        public Order() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public OrderItem getItems() {
            return items;
        }

        public void setItems(OrderItem items) {
            this.items = items;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", items=" + items +
                ", orderDate='" + orderDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
