package com.jb.delivery_food_app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantContact;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL) //one restaurant have many items, mapped by the variable - we used, cascade - affect all operation changes in both menuitem and rest
    private List<MenuItem> menuItems = new ArrayList<>(); // list the list of item in the menu using ArrayList


}
