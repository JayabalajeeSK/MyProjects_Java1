package com.jb.ems_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jb.ems_backend.entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}