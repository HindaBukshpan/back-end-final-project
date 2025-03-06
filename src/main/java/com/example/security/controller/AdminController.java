package com.example.security.controller;

import com.example.security.model.CustomUser;
import com.example.security.model.Item;
import com.example.security.model.Role;
import com.example.security.service.ItemService;
import com.example.security.service.UserService;
import com.example.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @GetMapping()
    public ResponseEntity<Boolean> isAdmin (@RequestHeader(value = "Authorization") String token){
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            CustomUser user = userService.getUserByUsername(username);
            if (username == null){
                return new ResponseEntity("you must enter the system to do this action", HttpStatus.BAD_REQUEST);
            }
            if (user.getRole() != Role.ADMIN) {
                return new ResponseEntity("you are not allowed to do this in the system.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-item")
    public ResponseEntity<String> createItem(@RequestHeader(value = "Authorization") String token, @RequestBody Item item) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            CustomUser user = userService.getUserByUsername(username);
            if (username == null){
                return new ResponseEntity("you must enter the system to add items.", HttpStatus.BAD_REQUEST);
            }
            if (user.getRole() != Role.ADMIN) {
                return new ResponseEntity("you are not allowed to add items to the system.", HttpStatus.BAD_REQUEST);
            }
            if (item == null){
                return new ResponseEntity("you must enter an item to create one.", HttpStatus.BAD_REQUEST);
            }
            String result = itemService.createItem(item);
            if (result.contains("successfully")) {
                return new ResponseEntity<>( result, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-item")
    public ResponseEntity<Item> updateItem(@RequestHeader(value = "Authorization") String token, @RequestBody Item updatedItem) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            CustomUser user = userService.getUserByUsername(username);
            if (username == null){
                return new ResponseEntity("you must enter the system to update items.", HttpStatus.BAD_REQUEST);
            }
            if (user.getRole() != Role.ADMIN) {
                return new ResponseEntity("you are not allowed to update items in the system.", HttpStatus.BAD_REQUEST);
            }
            if (itemService.getItemById(updatedItem.getId()) == null){
                return new ResponseEntity("did not update item. This item does not exist in the system.", HttpStatus.BAD_REQUEST);
            }
            if (updatedItem.getId() == null || updatedItem.getName() == null || updatedItem.getDescription().trim().isEmpty() || updatedItem.getPrice() == null || updatedItem.getStock() == null || updatedItem.getImageUrl() == null) {
                return new ResponseEntity("Item not update. id, name, description, price, stock & url image are required.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(itemService.updateItem(updatedItem), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@RequestHeader(value = "Authorization") String token, @PathVariable int itemId) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            CustomUser user = userService.getUserByUsername(username);
            Item itemFromDB = itemService.getItemById(itemId);
            if (username == null){
                return new ResponseEntity("you must enter the system to delete items.", HttpStatus.BAD_REQUEST);
            }
            if (user.getRole() != Role.ADMIN) {
                return new ResponseEntity("you are not allowed to delete items from the system.", HttpStatus.BAD_REQUEST);
            }
            if (itemFromDB == null) {
                return new ResponseEntity("Item not deleted, Because this item does not exist in the system.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(itemService.deleteItem(itemId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all-users")
    public ResponseEntity<List<CustomUser>> getAllUsers() {
        try {
            List<CustomUser> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }}
