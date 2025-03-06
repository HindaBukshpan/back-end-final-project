package com.example.security.repository.mapper;

import com.example.security.model.ItemOfWishList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemOfWishListMapper implements RowMapper<ItemOfWishList> {

        @Override
        public ItemOfWishList mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemOfWishList itemOfWishList = new ItemOfWishList();
            itemOfWishList.setId(rs.getInt("id"));
            itemOfWishList.setUsername(rs.getString("username"));
            itemOfWishList.setItemId(rs.getInt("item_id"));
            return itemOfWishList;

        }
}
