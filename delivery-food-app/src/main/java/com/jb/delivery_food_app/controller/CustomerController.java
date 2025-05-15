package com.jb.delivery_food_app.controller;

import com.jb.delivery_food_app.entity.Customer;
import com.jb.delivery_food_app.entity.MenuItem;
import com.jb.delivery_food_app.entity.Order;
import com.jb.delivery_food_app.service.CustomerService;
import com.jb.delivery_food_app.service.MenuItemService;
import com.jb.delivery_food_app.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cust")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/register")// //////////////////////////////
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
        // return ResponseEntity.ok("created");
    }

    @GetMapping("/customer/{id}")//
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/customer/{id}")// ///////////////////////////
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
        // return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/customer/{id}")// //////////////////////////
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Deleted"); // 204 No Content
    }

    ////////////////////////////////////

    @GetMapping("/menus/{restaurantId}")//
    public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable Long restaurantId) {
        List<MenuItem> items = menuItemService.getMenuItemsByRestaurantId(restaurantId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/order") //////////////////////////
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        Order placedOrder = orderService.placeOrder(order);
        return ResponseEntity.ok(placedOrder);
        // return ResponseEntity.ok("created");
    }

    @GetMapping("/order/{orderId}/status")
    public ResponseEntity<Order> trackOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
}