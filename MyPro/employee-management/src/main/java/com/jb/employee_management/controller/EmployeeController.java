package com.jb.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.server.ResponseStatusException;

import com.jb.employee_management.entity.Employee;
import com.jb.employee_management.service.impl.EmployeeServiceImpl;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired 
    private EmployeeServiceImpl employeeService;

    //ALL ROLES //
    @GetMapping("/employee/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    // ALL ROLES //
    @GetMapping("/employee/employees/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

     // ALL ROLES //
    @GetMapping("/employee/by-email")
    public Employee getByEmail(@RequestParam String email) {
        return employeeService.getByEmail(email);
    }

    // All ROLES //
    @GetMapping("/employee/employees")
    public ResponseEntity<Page<Employee>> getAllBySort(  //0 * 5 = 0 ; 1 - 5 || //1 * 5 = 5 ; 6 - 10 || //3 * 15 = 45 ; 46 - 60
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(employeeService.getAllWithSort(page, size, sortBy));
    }

    ///////////////////////////////////////////////////////
    
    // All ROLES 
//    @GetMapping("/employee/me")
//     public ResponseEntity<Employee> getOwnProfile(Authentication auth) {
//     Employee emp = employeeService.getByUsername(User.getB);
//     if (emp == null) {
//         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
//     }
//     return ResponseEntity.ok(emp);
// }
    ///////////////////////////////////////////////////////////

    // ADMIN ONLY //
    @PostMapping("/admin/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.create(employee));
    }

    // ADMIN + HR //
    @PutMapping("/hr/employees/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    // ADMIN //
    @DeleteMapping("/admin/employees/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok("Employee deleted successfully with ID: " + id);
    }

    // Optional: uncomment if needed in future
    // @GetMapping("/employee/limited")
    // public ResponseEntity<List<Employee>> getLimited() {
    //     return ResponseEntity.ok(employeeService.getAllWithoutSalary());
    // }
}
