package com.jb.delivery_food_app.service;

import java.util.List;

import com.jb.delivery_food_app.entity.Customer;
import com.jb.delivery_food_app.entity.Order;

public interface CustomerService {

    List<Customer> getAllCustomers();

    List<Order> getOrdersByCustomerId(Long customerId);

    Customer getCustomerById(Long customerId);

    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long customerId, Customer customer);
    void deleteCustomer(Long customerId);
}
