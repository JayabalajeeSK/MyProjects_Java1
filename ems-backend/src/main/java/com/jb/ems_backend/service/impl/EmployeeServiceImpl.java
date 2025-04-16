package com.jb.ems_backend.service.impl;

import org.springframework.stereotype.Service;

import com.jb.ems_backend.dto.EmployeeDto;
import com.jb.ems_backend.entity.Employee;
import com.jb.ems_backend.mapper.EmployeeMapper;
import com.jb.ems_backend.repository.EmployeeRepository;
import com.jb.ems_backend.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) 
    {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }
    
}