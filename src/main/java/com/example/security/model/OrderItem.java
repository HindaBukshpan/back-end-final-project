package com.example.security.model;

public class OrderItem {

    private Long id;  // מזהה של פריט הזמנה
    private Long orderId;  // מזהה ההזמנה שאליה שייך הפריט
    private Long itemId;  // מזהה המוצר
    private Integer quantity;  // כמות הפריט בהזמנה
    private Double totalPrice;  // המחיר הכולל של הפריט (מחיר * כמות)

    // Constructor ברירת מחדל
    public OrderItem() {
    }

    // Getter ו-Setter עבור כל השדות
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // חישוב מחירים
    public void calculateTotalPrice(Double unitPrice) {
        this.totalPrice = unitPrice * this.quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
