package com.jb.delivery_food_app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jb.delivery_food_app.entity.Order;
import com.jb.delivery_food_app.repository.OrderRepository;
import com.jb.delivery_food_app.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    // Constructor injection
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomer_CustomerId(customerId);
    }

    @Override
    public Order placeOrder(Order order) {
        // Additional validation or processing can be done here
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order existingOrder = getOrderById(orderId);
        existingOrder.setOrderStatus(newStatus);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
