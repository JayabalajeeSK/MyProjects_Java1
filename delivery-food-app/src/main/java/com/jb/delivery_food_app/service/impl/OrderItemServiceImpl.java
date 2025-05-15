package com.jb.delivery_food_app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jb.delivery_food_app.entity.OrderItem;
import com.jb.delivery_food_app.repository.OrderItemRepository;
import com.jb.delivery_food_app.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    // Constructor injection
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getAllOrderItems() ///////////////////////
    {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) /////////////////////
    {
        return orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + id));
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) //////////////
    {
        return orderItemRepository.findByOrder_OrderId(orderId);
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) //////////////////////
     {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) /////////////////////
    {
        orderItemRepository.deleteById(id);
    }
}
