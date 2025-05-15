package com.jb.delivery_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.delivery_food_app.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
