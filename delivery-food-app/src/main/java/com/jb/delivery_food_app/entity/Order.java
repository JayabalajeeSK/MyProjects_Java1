package com.jb.delivery_food_app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;
    private String orderStatus;
    private LocalDateTime orderedAt;

    @ManyToOne ////many order item belong to one Cust
    @JoinColumn(name = "customer_id") ////add that order id column with OrderItem Table - order table
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)//one order have many items(ordered), mapped by the variable - we used, cascade - affect all operation changes in both Order and OrderItem
    List<OrderItem> orderItems = new ArrayList<>();

}
