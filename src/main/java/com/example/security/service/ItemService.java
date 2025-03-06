package com.example.security.service;

import com.example.security.model.Item;
import com.example.security.repository.ItemRepository;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public String createItem(Item item) {
        if (item.getName() == null || item.getDescription().trim().isEmpty() || item.getPrice() == null || item.getStock() == null || item.getImageUrl() == null ) {
            return "Item not created, the name, description, price, stock & image url are required.";
        }
        return itemRepository.createItem(item);
    }

    public Item getItemById(int itemId){ return itemRepository.findItemById(itemId);
    }

    public List<Item> getAllItems(){return itemRepository.findAllItems();}

    public List<Item> getItemsBySearch(String search) {
        return itemRepository.findItemBySearch(search);
    }

    public Item updateItem(Item updatedItem) {
        return itemRepository.updateItem(updatedItem);
    }

    public String deleteItem(int itemId) {
        return itemRepository.deleteItem(itemId);
    }
}
