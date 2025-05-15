package com.jb.delivery_food_app.controller;

import com.jb.delivery_food_app.entity.MenuItem;
import com.jb.delivery_food_app.entity.Order;
import com.jb.delivery_food_app.service.MenuItemService;
import com.jb.delivery_food_app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderService orderService;

    // Browse menu items by restaurant
    @GetMapping("/menus/{restaurantId}")
    public List<MenuItem> getMenuItems(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurantId(restaurantId);
    }

    // Place a new order
    @PostMapping("/order")
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    // Track order status
    @GetMapping("/order/{orderId}/status")
    public Order trackOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
