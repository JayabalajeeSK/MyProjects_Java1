package com.jb.spring_boot_rest_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jb.spring_boot_rest_api.Entity.Student;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @GetMapping("/student")
    public ResponseEntity<Student> getStudent() {
        Student student = new Student(1, "Jayabalajee", "S K");
        return ResponseEntity.ok(student);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "jaya", "bala"));
        students.add(new Student(2, "jayabala", "S K"));
        students.add(new Student(3, "tharun", "bala"));
        students.add(new Student(4, "jb", "S K"));
        return ResponseEntity.ok(students);
    }

    @GetMapping("/student/{id}/{firstName}/{lastName}")
    public ResponseEntity<Student> getStudentByIdfirstNameLastName(
            @PathVariable int id,
            @PathVariable String firstName,
            @PathVariable String lastName) {

        Student student = new Student(id, firstName, lastName);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/student/query")
    public ResponseEntity<Student> studentRequestVariable(
            @RequestParam int id,
            @RequestParam String firstName,
            @RequestParam String lastName) {

        Student student = new Student(id, firstName, lastName);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        System.out.println(student.getId());
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return new ResponseEntity<>(student, HttpStatus.CREATED); // 201 Created
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        String message = id + ": student Deleted Successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
