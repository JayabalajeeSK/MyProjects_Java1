package com.jb.delivery_food_app.service;

import java.util.List;

import com.jb.delivery_food_app.entity.Order;

public interface OrderService {

    List<Order> getAllOrders(); // List all orders

    Order getOrderById(Long id); // Fetch order by ID

    List<Order> getOrdersByCustomerId(Long customerId); // Filter by customer

    Order placeOrder(Order order); // Place a new order
    Order updateOrderStatus(Long orderId, String newStatus); // Change order status
    void deleteOrder(Long orderId); // Cancel/Delete an order
}
