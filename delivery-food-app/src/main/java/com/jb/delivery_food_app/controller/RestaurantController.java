package com.jb.delivery_food_app.controller;

import com.jb.delivery_food_app.entity.MenuItem;
import com.jb.delivery_food_app.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private MenuItemService menuItemService;

    // Add menu item
    @PostMapping("/menu")
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.createMenuItem(menuItem);
    }

    // View all menu items of the restaurant
    @GetMapping("/menu/{restaurantId}")
    public List<MenuItem> getMenuItems(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurantId(restaurantId);
    }

    // Delete menu item
    @DeleteMapping("/menu/{menuItemId}")
    public void deleteMenuItem(@PathVariable Long menuItemId) {
        menuItemService.deleteMenuItem(menuItemId);
    }
}
