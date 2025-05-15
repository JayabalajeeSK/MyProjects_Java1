package com.jb.delivery_food_app.service;

import java.util.List;

import com.jb.delivery_food_app.entity.MenuItem;
import com.jb.delivery_food_app.entity.Restaurant;

public interface RestaurantService {

    List<Restaurant> getAllRestaurants();

    List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId);

    Restaurant getRestaurantById(Long id);
    
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant);
    void deleteRestaurant(Long id);

}
