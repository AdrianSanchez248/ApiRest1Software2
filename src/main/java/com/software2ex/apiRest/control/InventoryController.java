package com.software2ex.apiRest.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2ex.apiRest.model.Item;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private List<Item> inventory;

    public InventoryController() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Item[] itemsArray = objectMapper.readValue(new ClassPathResource("Item.json").getFile(), Item[].class);
            inventory = new ArrayList<>(Arrays.asList(itemsArray));
        } catch (IOException e) {
            e.printStackTrace();
            inventory = new ArrayList<>();
        }
    }

    @GetMapping
    public List<Item> getAllItems() {
        return inventory;
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return inventory.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        inventory.add(item);
        return item;
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item item = getItemById(id);
        if (item != null) {
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            item.setPrice(updatedItem.getPrice());
            return item;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        inventory.removeIf(item -> item.getId().equals(id));
    }
}
