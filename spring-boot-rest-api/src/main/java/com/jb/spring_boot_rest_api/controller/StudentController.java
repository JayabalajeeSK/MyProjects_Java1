package com.jb.spring_boot_rest_api.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jb.spring_boot_rest_api.Entity.Student;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @GetMapping("/student")
    public Student getStudent()
    {
        Student student = new Student(1, "Jayabalajee", "S K");
        return student;
    }

    @GetMapping("/all")
    public List<Student> getAllStudents()
    {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "jaya", "bala"));
        students.add(new Student(2, "jayabala", "S K"));
        students.add(new Student(3, "tharun", "bala"));
        students.add(new Student(4, "jb", "S K"));
        return students;
    }

    @GetMapping("/student/{id}/{firstName}/{lastName}")
    private Student getStudentByIdfirstNameLastName(@PathVariable int id, @PathVariable String firstName, @PathVariable String lastName)
    {
        return new Student(id, firstName, lastName);

    }

    @GetMapping("/student/query")
    private Student studentRequestVariable(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName)
    {
        return new Student(id, firstName, lastName);
    }

    @PostMapping("/addStudent")
    public Student createStudnet(@RequestBody Student student)
    {
        System.out.println(student.getId());
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return student;
    }

    @PutMapping("/{id}/update")
    public Student updateStudnet(@PathVariable("id") int id, @RequestBody Student student)
    {
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return student;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteStudent(@PathVariable int id)
    {
        return id+ ": student Deleted Successfully";
    }
}