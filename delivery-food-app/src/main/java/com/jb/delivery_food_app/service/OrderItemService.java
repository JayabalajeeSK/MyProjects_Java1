package com.jb.delivery_food_app.service;

import java.util.List;

import com.jb.delivery_food_app.entity.OrderItem;

public interface OrderItemService {

    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(Long id);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    OrderItem createOrderItem(OrderItem orderItem);
    void deleteOrderItem(Long id);
}
