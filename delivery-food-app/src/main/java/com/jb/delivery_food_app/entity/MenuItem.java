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
@Table(name = "menuitems")
public class MenuItem 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private double itemPrice;

    @ManyToOne //many menu items belong to one restaurant
    @JoinColumn(name= "restaurant_id") //add that rest id column with MenuItem Table
    private Restaurant restaurant; // menu created then restaurant
}
