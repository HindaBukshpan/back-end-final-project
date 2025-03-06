package com.example.security.repository;

import com.example.security.model.ItemOfWishList;
import com.example.security.repository.mapper.ItemOfWishListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String WISH_LIST_TABLE = "wish_list";

    public String aadItemToWishList(String username, Integer itemId){
        String sql = String.format("INSERT INTO %s (username, item_id) " +
                "VALUES (?, ?)", WISH_LIST_TABLE);
        jdbcTemplate.update(sql, username, itemId);
        return "item added to the wish list successfully";
    }

    public List<ItemOfWishList> getWishListByUserName(String username){
        String sql = String.format("SELECT * FROM %s WHERE username = ?", WISH_LIST_TABLE);
        return jdbcTemplate.query(sql, new ItemOfWishListMapper(), username);
    }

    public String deleteItemFromWishListByItemId(Integer itemId){
            String sql = String.format("DELETE FROM %s WHERE id = ?", WISH_LIST_TABLE);
            jdbcTemplate.update(sql, itemId);
            return "item deleted from wish list successfully";
    }

     public String deleteAllItemsFromWishListByUserName(String username){
         String sql = String.format("DELETE FROM %s WHERE username = ?", WISH_LIST_TABLE);
         jdbcTemplate.update(sql, username);
         return "wish items for this user were deleted from wish list successfully";

     }







}
