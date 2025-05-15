package com.jb.delivery_food_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.delivery_food_app.entity.Customer;
import com.jb.delivery_food_app.entity.Order;
import com.jb.delivery_food_app.repository.CustomerRepository;
import com.jb.delivery_food_app.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customer_id) {
        Optional<Customer> customerOpt = customerRepository.findById(customer_id);
        if (customerOpt.isPresent()) {
            return customerOpt.get().getOrders(); // from the List<Order> in Customer
        } else {
            throw new RuntimeException("Customer not found with ID: " + customer_id);
        }
    }

    @Override
    public Customer getCustomerById(Long customer_id) 
    {
        return customerRepository.findById(customer_id).orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customer_id));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long customer_id, Customer customer) {
        Customer existingCustomer = getCustomerById(customer_id);

        existingCustomer.setCustName(customer.getCustName());
        existingCustomer.setCustEmail(customer.getCustEmail());
        existingCustomer.setCustPhone(customer.getCustPhone());
        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long customer_id) 
    {
        Customer customer = getCustomerById(customer_id);
        customerRepository.delete(customer);
    }
}
