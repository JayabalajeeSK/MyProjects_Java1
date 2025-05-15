package com.jb.delivery_food_app.service;
import java.util.List;
import com.jb.delivery_food_app.entity.MenuItem;

public interface MenuItemService {

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId);

    MenuItem getMenuItemById(Long itemId);

    MenuItem createMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem(Long itemId, MenuItem updatedItem);
    void deleteMenuItem(Long itemId);
}