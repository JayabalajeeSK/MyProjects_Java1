package com.jb.delivery_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.delivery_food_app.entity.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
