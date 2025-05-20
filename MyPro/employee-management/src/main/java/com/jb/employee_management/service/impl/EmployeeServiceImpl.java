package com.jb.employee_management.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

// import com.jb.employee_management.config.SecurityConfig;
import com.jb.employee_management.entity.Employee;
import com.jb.employee_management.repo.EmployeeRepository;
import com.jb.employee_management.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;
//////////////////////////////////////////////
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	public Page<Employee> getAllWithSort(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return employeeRepository.findAll(pageable);
    }

///////////////////////////

	public Employee getById(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
	}
	
    public Employee getByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

	// public Employee getByUsername(SecurityConfig username) {
    // 	return employeeRepository.findByUsername(username);
    // }

//////////////////////////////
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElseThrow();

        employee.setName(updatedEmployee.getName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setJoiningDate(updatedEmployee.getJoiningDate());
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }


}