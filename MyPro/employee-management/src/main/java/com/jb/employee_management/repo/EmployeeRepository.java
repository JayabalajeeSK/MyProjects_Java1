package com.jb.employee_management.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.employee_management.config.SecurityConfig;
import com.jb.employee_management.entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
    Employee findByUsername(SecurityConfig username);
}
