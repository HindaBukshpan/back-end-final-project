package com.example.security.service;

import com.example.security.model.ItemOfWishList;
import com.example.security.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    public String addItemToWishList(String username, Integer itemId) {
        if (itemId == null || username == null){
            return "cannot add this item to your wish list";
        }
        return wishListRepository.aadItemToWishList(username, itemId);
    }


    public List<ItemOfWishList> getWishListByUserName(String username){
        if (username == null){
            return new ArrayList<>();
        }       return wishListRepository.getWishListByUserName(username);
    }

    public String deleteItemFromWishListByItemId(Integer itemId){

        if (itemId == null) {
            return "enter a valid item id";
            }
        return wishListRepository.deleteItemFromWishListByItemId(itemId);
        }

    public String deleteAllItemsFromWishListByUserName(String username){
        if (username == null){
            return "this user does not exist in the system";
        }
        return wishListRepository.deleteAllItemsFromWishListByUserName(username);

    }















}
