package com.jb.delivery_food_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.delivery_food_app.entity.MenuItem;
import com.jb.delivery_food_app.repository.MenuItemRepository;
import com.jb.delivery_food_app.repository.RestaurantRepository;
import com.jb.delivery_food_app.service.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<MenuItem> getAllMenuItems() ////////////////////
    {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) ///////////////////
    {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found with ID: " + restaurantId));
        return menuItemRepository.findByRestaurant_RestaurantId(restaurantId);
    }

    @Override
    public MenuItem getMenuItemById(Long itemId) ///////////////////////
    {
        return menuItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + itemId));
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) ////////////////////////
    {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long itemId, MenuItem updatedItem) ///////////////////
    {
        MenuItem existingItem = menuItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + itemId));

        existingItem.setItemName(updatedItem.getItemName());
        existingItem.setItemPrice(updatedItem.getItemPrice());
        existingItem.setRestaurant(updatedItem.getRestaurant());

        return menuItemRepository.save(existingItem);
    }

    @Override
    public void deleteMenuItem(Long itemId) ///////////////////////
    {
        menuItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + itemId));
        menuItemRepository.deleteById(itemId);
    }
}
