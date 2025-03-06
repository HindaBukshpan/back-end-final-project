package com.example.security.controller;

import com.example.security.model.ItemOfWishList;
import com.example.security.service.UserService;
import com.example.security.service.WishListService;
import com.example.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/wish-list")
public class WishListController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserService userService;


@PostMapping
    public String addItemToWishList(@RequestHeader(value = "Authorization") String token, Integer itemId) {
    String jwtToken = token.substring(7);
    String username = jwtUtil.extractUsername(jwtToken);
        if (itemId == null || username == null){
            return "cannot add this item to your wish list";
        }
        return wishListService.addItemToWishList(username, itemId);
    }

@GetMapping()
    public List<ItemOfWishList> getWishListByUserName(@RequestHeader(value = "Authorization") String token){
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
            if (username == null){
                return new ArrayList<>();
            }       return wishListService.getWishListByUserName(username);
        }

        @DeleteMapping(value = "delete-from-wish-list")
    public String deleteItemFromWishListByItemId(@RequestHeader(value = "Authorization") String token, Integer itemId){
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            if (username == null){
                return "this user does not exist in the system";
            }
        return wishListService.deleteItemFromWishListByItemId(itemId);
    }
@DeleteMapping("/delete-users-wish-list")
    public String deleteAllItemsFromWishListByUserName(@RequestHeader(value = "Authorization") String token, String username){
    String jwtToken = token.substring(7);
    String u = jwtUtil.extractUsername(jwtToken);
        if (username == null){
            return "this user does not exist in the system";
        }
        return wishListService.deleteAllItemsFromWishListByUserName(username);

    }
}
