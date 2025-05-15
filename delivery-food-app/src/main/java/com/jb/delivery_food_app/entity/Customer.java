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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String custName;
    private String custEmail;
    private String custPhone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) //one cust have many order, mapped by the variable - we used, cascade - affect all operation changes in both cust and Orders
    private List<Order> orders = new ArrayList<>();

}