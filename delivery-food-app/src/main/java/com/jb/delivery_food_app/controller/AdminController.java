package com.jb.delivery_food_app.controller;

import com.jb.delivery_food_app.entity.Order;
import com.jb.delivery_food_app.entity.OrderItem;
import com.jb.delivery_food_app.service.OrderService;
import com.jb.delivery_food_app.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/orders") // /////////////////////////////
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
        // return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/order/{orderId}/status") //////////////////////////
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        //Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("updated"); //updateorder
    }

    @DeleteMapping("/order/{orderId}") ///////////////////////
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Deleted"); // 204 No Content
    }
/////////////////////////////////////////////////////////////////
    @GetMapping("/orderitems")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() ///////////////////////////////
    {
        List<OrderItem> items = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(items);
        // return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @GetMapping("/orderitems/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long id) {
        OrderItem item = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/orderitems/{id}") /////////////////////////////
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok("Deleted"); // 204 No Content
    }
}
