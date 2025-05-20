package com.jb.employee_management.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.jb.employee_management.entity.Employee;

public interface EmployeeService {

    
    
    List<Employee> getAll();
    Page<Employee> getAllWithSort(int page, int size, String sortBy);

    Employee getById(Long id);
    Employee getByEmail(String email);

    Employee create(Employee employee);
    Employee update(Long id, Employee updatedEmployee);
    void delete(Long id);
}
