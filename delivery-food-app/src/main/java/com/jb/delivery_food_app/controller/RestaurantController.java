package com.jb.delivery_food_app.controller;

import com.jb.delivery_food_app.entity.MenuItem;
import com.jb.delivery_food_app.entity.Restaurant;
import com.jb.delivery_food_app.service.MenuItemService;
import com.jb.delivery_food_app.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/add") // //////////////////////////
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant saved = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(saved);
        // return ResponseEntity.ok("created");
    }

    @GetMapping("/{id}") //
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    @PutMapping("/{id}") // //////////////////////////
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updated = restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok(updated);
        // return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/{id}") // ///////////////////////////
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok("Deleted");
    }

    /////////////// MENU //////////////////

    @PostMapping("/menu") // //////////////////////////////
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem saved = menuItemService.createMenuItem(menuItem);
        return ResponseEntity.ok(saved);
        // return ResponseEntity.ok("created");
    }

    @GetMapping("/menu/{restaurantId}") // //////////////////////////////
    public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable Long restaurantId) {
        List<MenuItem> items = menuItemService.getMenuItemsByRestaurantId(restaurantId);
        return ResponseEntity.ok(items);
        // return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurantId(restaurantId));
    }

    @GetMapping("/menu/item/{menuItemId}") //
    public ResponseEntity<MenuItem> getMenuItem(@PathVariable Long menuItemId) {
        MenuItem item = menuItemService.getMenuItemById(menuItemId);
        return ResponseEntity.ok(item);
    }
 
    @PutMapping("/menu/{menuItemId}") // /////////////////////////
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long menuItemId, @RequestBody MenuItem menuItem) {
        MenuItem updated = menuItemService.updateMenuItem(menuItemId, menuItem);
        return ResponseEntity.ok(updated);
        // return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/menu/{menuItemId}") // ///////////////
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuItemId) 
    {
        menuItemService.deleteMenuItem(menuItemId);
        return ResponseEntity.ok("Deleted");
    }
}
