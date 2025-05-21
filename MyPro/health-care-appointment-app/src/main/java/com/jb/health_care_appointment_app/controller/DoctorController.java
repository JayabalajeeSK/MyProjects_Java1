package com.jb.health_care_appointment_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.service.impl.DoctorServiceImpl;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @GetMapping("/appointments/{doctorId}")
    public ResponseEntity<List<Appointment>> appointments(@PathVariable Long doctorId) {
        List<Appointment> list = doctorService.getAppointmentsForDoctor(doctorId);
        return ResponseEntity.ok(list);
    }

}
