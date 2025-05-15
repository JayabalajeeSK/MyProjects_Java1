package com.jb.delivery_food_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitemId;
    private int quantity;
    private double orderPrice;
    
    @ManyToOne //many order item belong to one order
    @JoinColumn(name = "order_id") //add that order id column with OrderItem Table
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id") //add that menu item id column with OrderItem Table
    private MenuItem menuItem;



}
