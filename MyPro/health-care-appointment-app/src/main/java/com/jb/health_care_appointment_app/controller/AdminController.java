package com.jb.health_care_appointment_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.entity.Doctor;
import com.jb.health_care_appointment_app.service.impl.AdminServiceImpl;
import com.jb.health_care_appointment_app.service.impl.DoctorServiceImpl;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private DoctorServiceImpl doctorService;

    @GetMapping("/appointments") //
    public ResponseEntity<List<Appointment>> allAppointments() {
        List<Appointment> list = adminService.getAllAppointments();
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/appointments/{doctorId}")
    public ResponseEntity<List<Appointment>> appointments(@PathVariable Long doctorId) {
        List<Appointment> list = doctorService.getAppointmentsForDoctor(doctorId);
        return ResponseEntity.ok(list);
    }

//////////////////////////////////////////////////

    @PostMapping("/doctor") //
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(adminService.addDoctor(doctor), HttpStatus.OK);
    }

    @PutMapping("/doctor/{id}") //
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor updated) {
        return new ResponseEntity<>(adminService.updateDoctor(id, updated), HttpStatus.OK);
    }

    @DeleteMapping("/doctor/{id}") //
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.ok("Deleted");
    }


}

