package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemOfWishList {

    private Integer id;
    private String username;
    @JsonProperty(value = "item_id")
    private Integer itemId;

    public ItemOfWishList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "ItemOfWishList{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", itemId=" + itemId +
                '}';
    }
}
