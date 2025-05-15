package com.jb.delivery_food_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.delivery_food_app.entity.MenuItem;
import com.jb.delivery_food_app.entity.Restaurant;
import com.jb.delivery_food_app.repository.MenuItemRepository;
import com.jb.delivery_food_app.repository.RestaurantRepository;
import com.jb.delivery_food_app.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) 
    {
        // Assuming MenuItemRepository has a method: List<MenuItem> findByRestaurantId(Long restaurantId);
        return menuItemRepository.findByRestaurant_RestaurantId(restaurantId);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        return optionalRestaurant.orElse(null); // or throw an exception if preferred
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, Restaurant updatedRestaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant existingRestaurant = optionalRestaurant.get();
            existingRestaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
            existingRestaurant.setRestaurantAddress(updatedRestaurant.getRestaurantAddress());
            existingRestaurant.setRestaurantContact(updatedRestaurant.getRestaurantContact());
            // update other fields as necessary
            return restaurantRepository.save(existingRestaurant);
        }
        return null; // or throw exception if restaurant not found
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
