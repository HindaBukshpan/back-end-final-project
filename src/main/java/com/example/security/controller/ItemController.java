package com.example.security.controller;

import com.example.security.model.Item;
import com.example.security.service.ItemService;
import com.example.security.service.UserService;
import com.example.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @GetMapping()
    public ResponseEntity<List<Item>> getAllItemBySearch(
            @RequestParam(value = "search", required = false) String search) {
        try {
            List<Item> items;
            if (search != null && !search.isEmpty()) {
                items = itemService.getItemsBySearch(search);
                return ResponseEntity.ok(items);
            } else {
                items = itemService.getAllItems();
                return ResponseEntity.ok(items);
            }
        } catch (Exception e) {
            System.out.println("שגיאה בהבאת פריטים: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}