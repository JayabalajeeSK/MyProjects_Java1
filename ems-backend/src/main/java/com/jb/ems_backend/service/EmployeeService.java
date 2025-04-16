package com.jb.ems_backend.service;
import com.jb.ems_backend.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long employeeId);

}